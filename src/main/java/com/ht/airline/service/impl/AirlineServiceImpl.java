package com.ht.airline.service.impl;

import static com.ht.airline.common.Constant.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.airline.common.Constant;
import com.ht.airline.common.utils.AirlineUtils;
import com.ht.airline.db.entity.*;
import com.ht.airline.db.repository.*;
import com.ht.airline.dto.common.BookPassengerDTO;
import com.ht.airline.dto.common.PassengerDTO;
import com.ht.airline.dto.common.SeatAvailabilityDTO;
import com.ht.airline.dto.common.SeatPassengerDTO;
import com.ht.airline.dto.request.*;
import com.ht.airline.dto.response.*;
import com.ht.airline.security.RandomStringGenerator;
import com.ht.airline.service.AirlineService;
import com.ht.airline.service.EmailService;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AirlineServiceImpl implements AirlineService {

  @Autowired AirportRepository airportRepository;
  @Autowired FlightRepository flightRepository;
  @Autowired AirCarrierRepository airCarrierRepository;
  @Autowired CustomerRepository customerRepository;
  @Autowired RandomStringGenerator randomStringGenerator;
  @Autowired PassengerRepository passengerRepository;
  @Autowired BookingRepository bookingRepository;
  @Autowired ObjectMapper objectMapper;
  @Autowired EmailService emailService;

  @Value("${app.seats-availability.economy}")
  private Integer economySeats;

  @Value("${app.seats-availability.premium}")
  private Integer premiumSeats;

  @Value("${app.seats-availability.business}")
  private Integer businessSeats;

  @Override
  public ListAirportResponse getAirports() {
    List<Airport> airports = airportRepository.findAll();
    List<AirportResponse> airportResponses = new ArrayList<>();
    for (Airport airport : airports) {
      airportResponses.add(
          AirportResponse.builder()
              .id(airport.getId())
              .city(airport.getCity())
              .cityCode(airport.getCityCode())
              .airportName(airport.getName())
              .build());
    }
    return ListAirportResponse.builder()
        .statusCode(HttpStatus.OK.value())
        .data(airportResponses)
        .build();
  }

  private boolean isAdmin(String email) {
    return customerRepository.findByEmailAndAdmin(email, true) != null;
  }

  @Override
  public GenFlightResponse createFlight(CreateFlightRequest req) {
    GenFlightResponse response =
        GenFlightResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value()).build();
    Timestamp departure = new Timestamp(Long.parseLong(req.getDepartureTs()));
    Timestamp arrival = new Timestamp(Long.parseLong(req.getArrivalTs()));
    if (isAdmin(req.getEmail())) {
      if (departure.before(arrival)
          && departure.before(new Timestamp(System.currentTimeMillis()))) {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .description("Invalid timestamp")
                .build());
      } else {
        Optional<AirCarrier> airCarrier = airCarrierRepository.findById(req.getAirCarrierId());
        Optional<Airport> sourceAirport = airportRepository.findById(req.getSourceAirportId());
        Optional<Airport> destinationAirport =
            airportRepository.findById(req.getDestinationAirportId());
        if (airCarrier.isPresent() && sourceAirport.isPresent() && destinationAirport.isPresent()) {
          AirCarrier carrier = airCarrier.get();
          Double economyPrice =
              getEconomyPrice(
                  sourceAirport.get(), destinationAirport.get(), carrier.getBasePrice());
          Flight flight =
              Flight.builder()
                  .airCarrierId(req.getAirCarrierId())
                  .sourceAirportId(req.getSourceAirportId())
                  .destinationAirportId(req.getDestinationAirportId())
                  .departureDate(new Date(departure.getTime()))
                  .departureTs(departure)
                  .economyPrice(economyPrice)
                  .premiumEconomyPrice(economyPrice * airCarrier.get().getPremiumMultiplier())
                  .businessPrice(economyPrice * airCarrier.get().getBusinessMultiplier())
                  .economySeats(AirlineUtils.getSeatString(economySeats))
                  .premiumSeats(AirlineUtils.getSeatString(premiumSeats))
                  .businessSeats(AirlineUtils.getSeatString(businessSeats))
                  .arrivalTs(arrival)
                  .status(Constant.UPCOMING_STATUS)
                  .build();
          Flight save = flightRepository.save(flight);
          FlightResponse flightResponse =
              getFlightResponse(
                  save,
                  carrier.getName(),
                  arrival,
                  departure,
                  flight.getEconomyPrice(),
                  flight.getPremiumEconomyPrice(),
                  flight.getBusinessPrice());
          response.setStatusCode(HttpStatus.OK.value());
          response.setData(Collections.singletonList(flightResponse));
        } else {
          response.setError(
              ErrResponse.builder()
                  .errorCode(HttpStatus.BAD_REQUEST.value())
                  .description("Invalid Air carrier id or source/destination airport id")
                  .build());
        }
      }
    } else {
      response.setError(getAdminErrorResponse());
    }
    return response;
  }

  private ErrResponse getAdminErrorResponse() {
    return ErrResponse.builder()
        .errorCode(HttpStatus.UNAUTHORIZED.value())
        .description("Require admin permissions.")
        .build();
  }

  @Override
  public GenFlightResponse getFlight(String flightId) {
    GenFlightResponse response =
        GenFlightResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    Optional<Flight> flightOptional = flightRepository.findById(flightId);
    if (flightOptional.isPresent()) {
      Flight flight = flightOptional.get();
      response.setStatusCode(HttpStatus.OK.value());
      Optional<AirCarrier> airCarrier = airCarrierRepository.findById(flight.getAirCarrierId());
      FlightResponse flightResponse =
          getFlightResponse(
              flight,
              airCarrier.get().getId(),
              flight.getArrivalTs(),
              flight.getDepartureTs(),
              flight.getEconomyPrice(),
              flight.getPremiumEconomyPrice(),
              flight.getBusinessPrice());
      response.setData(Collections.singletonList(flightResponse));
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.NOT_FOUND.value())
              .description("Flight not found")
              .build());
    }
    return response;
  }

  @Override
  public GenFlightResponse updateFlight(UpdateFlightRequest req) {
    GenFlightResponse response =
        GenFlightResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    ErrResponse errResponse =
        ErrResponse.builder().errorCode(HttpStatus.BAD_REQUEST.value()).build();
    if (isAdmin(req.getEmail())) {
      ArrayList<Booking> allByFlightId = bookingRepository.findAllByFlightId(req.getFlightId());
      if (!allByFlightId.isEmpty()) {
        response.setStatusCode(HttpStatus.FORBIDDEN.value());
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .description("This flight already has some booking.")
                .build());
        return response;
      }
      Optional<Flight> flightOptional = flightRepository.findById(req.getFlightId());
      if (flightOptional.isPresent()) {
        Flight flight = flightOptional.get();
        if (req.getAirCarrierId() != null) {
          Optional<AirCarrier> airCarrier = airCarrierRepository.findById(req.getAirCarrierId());
          if (airCarrier.isPresent()) flight.setAirCarrierId(req.getAirCarrierId());
          else {
            errResponse.setDescription("Invalid Air Carrier Id");
            response.setError(errResponse);
            return response;
          }
        }
        if (req.getSourceAirportId() != null) {
          Optional<Airport> sourceAirport = airportRepository.findById(req.getSourceAirportId());
          if (sourceAirport.isPresent()) {
            flight.setSourceAirportId(req.getSourceAirportId());
          } else {
            errResponse.setDescription("Invalid Source Airport Id");
            response.setError(errResponse);
            return response;
          }
        }
        if (req.getDestinationAirportId() != null) {
          Optional<Airport> targetAirport =
              airportRepository.findById(req.getDestinationAirportId());
          if (targetAirport.isPresent()) {
            flight.setDestinationAirportId(req.getDestinationAirportId());
          } else {
            errResponse.setDescription("Invalid Destination Airport Id");
            response.setError(errResponse);
            return response;
          }
        }
        if (req.getDepartureTs() != null) {
          flight.setDepartureTs(getTimestampFromString(req.getDepartureTs()));
        }
        if (req.getArrivalTs() != null) {
          flight.setArrivalTs(getTimestampFromString(req.getArrivalTs()));
        }
        if (req.getBusinessPrice() != null) {
          flight.setBusinessPrice(req.getBusinessPrice());
        }
        if (req.getEconomyPrice() != null) {
          flight.setEconomyPrice(req.getEconomyPrice());
        }
        if (req.getPremiumEconomyPrice() != null) {
          flight.setPremiumEconomyPrice(req.getPremiumEconomyPrice());
        }
        flightRepository.save(flight);
        response.setStatusCode(HttpStatus.OK.value());
        Optional<AirCarrier> airCarrier = airCarrierRepository.findById(flight.getAirCarrierId());
        FlightResponse flightResponse =
            getFlightResponse(
                flight,
                airCarrier.get().getId(),
                flight.getArrivalTs(),
                flight.getDepartureTs(),
                flight.getEconomyPrice(),
                flight.getPremiumEconomyPrice(),
                flight.getBusinessPrice());
        response.setData(Collections.singletonList(flightResponse));
      } else {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Flight not found")
                .build());
      }
    } else {
      response.setError(getAdminErrorResponse());
    }
    return response;
  }

  private Timestamp getTimestampFromString(String timestamp) {
    return new Timestamp(Long.parseLong(timestamp));
  }

  private FlightResponse getFlightResponse(
      Flight flight,
      String id,
      Timestamp arrivalTs,
      Timestamp departureTs,
      Double economyPrice,
      Double premiumEconomyPrice,
      Double businessPrice) {
    return FlightResponse.builder()
        .airCarrierId(id)
        .id(flight.getId())
        .arrivalTs(arrivalTs)
        .sourceAirportId(flight.getSourceAirportId())
        .destinationAirportId(flight.getDestinationAirportId())
        .departureTs(departureTs)
        .economyPrice(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, economyPrice))
        .premiumEconomyPrice(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, premiumEconomyPrice))
        .businessPrice(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, businessPrice))
        .status(flight.getStatus())
        .seatAvailability(getAvailableSeats(flight))
        .build();
  }

  private SeatAvailabilityDTO getAvailableSeats(Flight flight) {
    return SeatAvailabilityDTO.builder()
        .businessSeats(Arrays.asList(flight.getBusinessSeats().split(COMMA_REGEX, -1)))
        .economySeats(Arrays.asList(flight.getEconomySeats().split(COMMA_REGEX, -1)))
        .premiumSeats(Arrays.asList(flight.getPremiumSeats().split(COMMA_REGEX, -1)))
        .build();
  }

  @Override
  public CommonResponse deleteFlight(DeleteFlightRequest request) {
    CommonResponse response =
        CommonResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    if (isAdmin(request.getEmail())) {
      Optional<Flight> byId = flightRepository.findById(request.getFlightId());
      if (byId.isPresent()) {
        Flight flight = byId.get();
        flight.setStatus(CANCELLED_STATUS);
        flightRepository.save(flight);
        // Cancel bookings if they are present
        ArrayList<Booking> bookings = bookingRepository.findAllByFlightId(request.getFlightId());
        for (Booking booking : bookings) {
          booking.setStatus(CANCELLED_STATUS);
          Optional<Customer> optionalCustomer =
              customerRepository.findById(booking.getCustomerId());
          if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            String message =
                "Booking was cancelled for customer "
                    + customer.getFirstName()
                    + " "
                    + customer.getLastName()
                    + " with id "
                    + booking.getId()
                    + " by HT airline";
            emailService.sendConfirmationMail(
                customer.getEmail(), message, "Booking cancelled", customer.getFirstName());
          }
        }
        bookingRepository.saveAll(bookings);
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(Collections.singletonList("Successfully deleted"));
      } else {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Flight not found")
                .build());
      }
    } else {
      response.setError(getAdminErrorResponse());
    }
    return response;
  }

  @Override
  public CommonResponse generateFlights() {
    List<Airport> airports = airportRepository.findAll();
    ArrayList<Flight> flights = new ArrayList<>();
    for (Airport source : airports) {
      for (Airport target : airports) {
        if (source != target) {
          for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
              Timestamp departureTs = AirlineUtils.getRandomTimeStamp(i);
              AirCarrier airCarrier = airCarrierRepository.findAll().get(0);
              Double economyPrice = getEconomyPrice(source, target, airCarrier.getBasePrice());
              Flight flight =
                  Flight.builder()
                      .airCarrierId(airCarrier.getId())
                      .sourceAirportId(source.getId())
                      .destinationAirportId(target.getId())
                      .departureDate(new Date(departureTs.getTime()))
                      .departureTs(departureTs)
                      .economyPrice(economyPrice)
                      .premiumEconomyPrice(economyPrice * airCarrier.getPremiumMultiplier())
                      .businessPrice(economyPrice * airCarrier.getBusinessMultiplier())
                      .economySeats(AirlineUtils.getSeatString(economySeats))
                      .premiumSeats(AirlineUtils.getSeatString(premiumSeats))
                      .businessSeats(AirlineUtils.getSeatString(businessSeats))
                      .arrivalTs(
                          AirlineUtils.getFlightDuration(
                              departureTs,
                              AirlineUtils.distance(
                                  Double.parseDouble(source.getLatitude()),
                                  Double.parseDouble(source.getLongitude()),
                                  Double.parseDouble(target.getLatitude()),
                                  Double.parseDouble(target.getLongitude()),
                                  'k')))
                      .status(Constant.UPCOMING_STATUS)
                      .build();
              flights.add(flight);
            }
          }
        }
      }
    }
    flightRepository.saveAll(flights);
    return CommonResponse.builder()
        .statusCode(HttpStatus.OK.value())
        .data(Collections.singletonList("Generated Flights."))
        .build();
  }

  @Override
  public SearchFlightResponse searchFlights(SearchFlightRequest req) {
    ArrayList<Flight> flights =
        flightRepository.findBySourceAirportIdAndDestinationAirportIdAndDepartureDate(
            req.getSourceAirportId(),
            req.getDestinationAirportId(),
            new Date(req.getDepartureTs().getTime()));
    ArrayList<FlightResponse> flightResponses = new ArrayList<>();
    for (Flight flight : flights) {
      Optional<AirCarrier> airCarrier = airCarrierRepository.findById(flight.getAirCarrierId());
      FlightResponse flightResponse =
          getFlightResponse(
              flight,
              airCarrier.get().getName(),
              flight.getArrivalTs(),
              flight.getDepartureTs(),
              flight.getEconomyPrice(),
              flight.getPremiumEconomyPrice(),
              flight.getBusinessPrice());
      flightResponses.add(flightResponse);
    }
    return SearchFlightResponse.builder()
        .statusCode(HttpStatus.OK.value())
        .data(flightResponses)
        .build();
  }

  public double getCostAsSeatClass(String c, Flight flight) {
    if (c.equals(Constant.ECONOMY_CLASS)) return flight.getEconomyPrice();
    else if (c.equals(Constant.PREMIUM_CLASS)) return flight.getPremiumEconomyPrice();
    else return flight.getBusinessPrice();
  }

  @Override
  public GenBookFlightResponse bookFlight(BookFlightRequest req) {
    GenBookFlightResponse response =
        GenBookFlightResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    Flight flight = flightRepository.findByIdAndStatus(req.getFlightId(), Constant.UPCOMING_STATUS);
    if (flight != null) {
      Customer customer = customerRepository.findByEmail(req.getEmail());
      if (customer != null) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        ArrayList<SeatPassengerDTO> seatPassengerDTOS = new ArrayList<>();
        for (PassengerDTO passengerDTO : req.getPassengers()) {
          seatPassengerDTOS.add(
              SeatPassengerDTO.builder()
                  .seatClass(passengerDTO.getSeatClass())
                  .seatNumber(passengerDTO.getSeatNumber())
                  .build());
        }
        if (checkSeatConfigurationAvailability(new String[0], seatPassengerDTOS, flight)) {
          for (PassengerDTO passengerDTO : req.getPassengers()) {
            Passenger passenger =
                Passenger.builder()
                    .email(passengerDTO.getEmail())
                    .firstName(passengerDTO.getFirstName())
                    .lastName(passengerDTO.getLastName())
                    .mobileNumber(passengerDTO.getMobileNumber())
                    .email(passengerDTO.getEmail())
                    .pnr(randomStringGenerator.generateRandomPnr())
                    .seatNumber(passengerDTO.getSeatNumber())
                    .seatClass(passengerDTO.getSeatClass())
                    .build();
            passengers.add(passenger);
          }
        } else {
          return getSeatUnavailableResponse(response);
        }
        List<Passenger> passengerList = passengerRepository.saveAll(passengers);
        StringBuilder passengerIds = new StringBuilder();
        ArrayList<BookPassengerDTO> passengerDTOS = new ArrayList<>();
        double totalAmount = 0.0;
        for (Passenger passenger : passengerList) {
          passengerIds.append(passenger.getId()).append(COMMA_REGEX);
          passengerDTOS.add(objectMapper.convertValue(passenger, BookPassengerDTO.class));
          totalAmount += getCostAsSeatClass(passenger.getSeatClass(), flight);
        }
        double totalCost = totalAmount;
        totalAmount = rewardCalculation(req.getRewards(), customer, totalAmount);
        Double rewardsUsed = totalCost - totalAmount;
        Booking booking =
            Booking.builder()
                .flightId(req.getFlightId())
                .customerId(customer.getId())
                .passengerIds(passengerIds.substring(0, passengerIds.length() - 1))
                .amount(totalAmount)
                .status(flight.getStatus())
                .rewardsUsed(rewardsUsed)
                .build();
        Booking save = bookingRepository.save(booking);
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(
            Collections.singletonList(
                BookFlightResponse.builder()
                    .bookingId(save.getId())
                    .totalAmount(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, totalAmount))
                    .message("Booking successful")
                    .passengers(passengerDTOS)
                    .build()));
        String message =
            new StringBuilder()
                .append("Booking for customer ")
                .append(customer.getFirstName())
                .append(" ")
                .append(customer.getLastName())
                .append(" from ")
                .append(airportRepository.findById(flight.getSourceAirportId()).get().getName())
                .append(" to ")
                .append(
                    airportRepository.findById(flight.getDestinationAirportId()).get().getName())
                .append(" was successful.")
                .toString();
        emailService.sendConfirmationMail(
            customer.getEmail(), message, "Booking Successful", customer.getFirstName());
      } else {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Customer not found")
                .build());
      }
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.NOT_FOUND.value())
              .description("Flight not found with upcoming status")
              .build());
    }
    return response;
  }

  private GenBookFlightResponse getSeatUnavailableResponse(GenBookFlightResponse response) {
    response.setStatusCode(HttpStatus.BAD_REQUEST.value());
    response.setError(
        ErrResponse.builder()
            .errorCode(HttpStatus.BAD_REQUEST.value())
            .description("Seat is not available for given seat numbers.")
            .build());
    return response;
  }

  private boolean checkSeatConfigurationAvailability(
      String[] passengerIds, ArrayList<SeatPassengerDTO> passengerDTOS, Flight flight) {
    ArrayList<String> eSeats =
        new ArrayList<>(Arrays.asList(flight.getEconomySeats().split(COMMA_REGEX)));
    ArrayList<String> pSeats =
        new ArrayList<>(Arrays.asList(flight.getPremiumSeats().split(COMMA_REGEX)));
    ArrayList<String> bSeats =
        new ArrayList<>(Arrays.asList(flight.getBusinessSeats().split(COMMA_REGEX)));
    // Add previous seats to search space
    for (String id : passengerIds) {
      Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
      if (optionalPassenger.isPresent()) {
        Passenger passenger = optionalPassenger.get();
        if (passenger.getSeatClass() != null && passenger.getSeatNumber() != null) {
          if (passenger.getSeatClass().equals(PREMIUM_CLASS) && passenger.getSeatNumber() != null)
            pSeats.add(passenger.getSeatNumber());
          else if (passenger.getSeatClass().equals(ECONOMY_CLASS))
            eSeats.add(passenger.getSeatNumber());
          else bSeats.add(passenger.getSeatNumber());
        }
      }
    }
    // Check for new seat numbers
    for (SeatPassengerDTO seatPassengerDTO : passengerDTOS) {
      if (seatPassengerDTO.getSeatClass().equals(ECONOMY_CLASS)) {
        boolean found = eSeats.stream().anyMatch(p -> p.equals(seatPassengerDTO.getSeatNumber()));
        if (!found) return false;
        eSeats.remove(seatPassengerDTO.getSeatNumber());
      } else if (seatPassengerDTO.getSeatClass().equals(PREMIUM_CLASS)) {
        boolean found = pSeats.stream().anyMatch(p -> p.equals(seatPassengerDTO.getSeatNumber()));
        if (!found) return false;
        pSeats.remove(seatPassengerDTO.getSeatNumber());
      } else {
        boolean found = bSeats.stream().anyMatch(p -> p.equals(seatPassengerDTO.getSeatNumber()));
        if (!found) return false;
        bSeats.remove(seatPassengerDTO.getSeatNumber());
      }
    }
    flight.setEconomySeats(String.join(COMMA_REGEX, eSeats));
    flight.setPremiumSeats(String.join(COMMA_REGEX, pSeats));
    flight.setBusinessSeats(String.join(COMMA_REGEX, bSeats));
    flightRepository.save(flight);
    return true;
  }

  @Override
  public GenBookFlightResponse selectSeat(SelectSeatRequest req) {
    GenBookFlightResponse response =
        GenBookFlightResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    Customer customer = customerRepository.findByEmail(req.getEmail());
    if (customer != null) {
      Booking booking =
          bookingRepository.findByIdAndStatus(req.getBookingId(), Constant.UPCOMING_STATUS);
      if (booking != null) {
        Optional<Flight> optionalFlight = flightRepository.findById(booking.getFlightId());
        Flight flight = optionalFlight.get();
        String passengerIds = booking.getPassengerIds();
        String[] pIds = passengerIds.split(COMMA_REGEX);
        ArrayList<SeatPassengerDTO> passengers = req.getPassengers();
        Double prevTotalAmount = booking.getAmount();
        double newTotalAmount = 0.0;
        ArrayList<BookPassengerDTO> passengerDTOS = new ArrayList<>();

        if (checkSeatConfigurationAvailability(pIds, passengers, flight)) {
          for (SeatPassengerDTO seatPassengerDTO : passengers) {
            Optional<Passenger> optionalPassenger =
                passengerRepository.findById(seatPassengerDTO.getId());
            if (optionalPassenger.isPresent()) {
              Passenger passenger = optionalPassenger.get();
              passenger.setSeatNumber(seatPassengerDTO.getSeatNumber());
              passenger.setSeatClass(seatPassengerDTO.getSeatClass());
              passengerRepository.save(passenger);
              passengerDTOS.add(objectMapper.convertValue(passenger, BookPassengerDTO.class));
              newTotalAmount += getCostAsSeatClass(passenger.getSeatClass(), flight);
            }
          }
        } else {
          return getSeatUnavailableResponse(response);
        }
        newTotalAmount = newTotalAmount - booking.getRewardsUsed() - prevTotalAmount;
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(
            Collections.singletonList(
                BookFlightResponse.builder()
                    .bookingId(booking.getId())
                    .totalAmount(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, newTotalAmount))
                    .message("Booking updated successful")
                    .passengers(passengerDTOS)
                    .build()));
        return response;
      } else {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Booking not found")
                .build());
      }
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.NOT_FOUND.value())
              .description("Customer not found")
              .build());
    }
    return response;
  }

  @Override
  public ListBookingsResponse listBookedFlights(ListBookFlightRequest req) {
    ListBookingsResponse response =
        ListBookingsResponse.builder().statusCode(HttpStatus.NOT_FOUND.value()).build();
    Customer customer = customerRepository.findByEmail(req.getEmail());
    if (customer != null) {
      ArrayList<Booking> bookings = bookingRepository.findAllByCustomerId(customer.getId());
      List<ListBookFlightResponse> responses = new ArrayList<>();
      for (Booking booking : bookings) {
        String passengerIds = booking.getPassengerIds();
        String[] pIds = passengerIds.split(COMMA_REGEX);
        ArrayList<BookPassengerDTO> passengerDTOS = new ArrayList<>();
        for (String id : pIds) {
          Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
          if (optionalPassenger.isPresent()) {
            passengerDTOS.add(
                objectMapper.convertValue(optionalPassenger.get(), BookPassengerDTO.class));
          }
        }
        Optional<Flight> optionalFlight = flightRepository.findById(booking.getFlightId());
        String sourceAirportId = optionalFlight.get().getSourceAirportId();
        String destinationAirportId = optionalFlight.get().getDestinationAirportId();
        responses.add(
            ListBookFlightResponse.builder()
                .bookingId(booking.getId())
                .flightId(optionalFlight.get().getId())
                .sourceAirport(airportRepository.findById(sourceAirportId).get().getName())
                .destinationAirport(
                    airportRepository.findById(destinationAirportId).get().getName())
                .passengers(passengerDTOS)
                .totalAmount(String.format(Constant.DOUBLE_TWO_DECIMAL_FORMAT, booking.getAmount()))
                .status(booking.getStatus())
                .build());
      }
      response.setData(responses);
      response.setStatusCode(HttpStatus.OK.value());
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.NOT_FOUND.value())
              .description("Customer not found")
              .build());
    }
    return response;
  }

  public void addSeat(String number, String seatClass, Flight flight) {
    if (seatClass.equals(Constant.ECONOMY_CLASS)) {
      addSeatToFlight(flight.getEconomySeats(), number, flight);
    } else if (seatClass.equals(Constant.PREMIUM_CLASS)) {
      addSeatToFlight(flight.getPremiumSeats(), number, flight);
    } else {
      addSeatToFlight(flight.getBusinessSeats(), number, flight);
    }
  }

  private void addSeatToFlight(String oldSeats, String number, Flight flight) {
    oldSeats += COMMA_REGEX + number;
    List<String> seats = Arrays.asList(oldSeats.split(COMMA_REGEX, -1));
    List<String> sortedSeats =
        seats.stream()
            .map(Integer::valueOf)
            .sorted()
            .map(String::valueOf)
            .collect(Collectors.toList());
    flight.setEconomySeats(String.join(COMMA_REGEX, sortedSeats));
    flightRepository.save(flight);
  }

  @Override
  public CommonResponse cancelBookedFlight(DeleteBookedFlightRequest req) {
    Booking booking =
        bookingRepository.findByIdAndStatus(req.getBookingId(), Constant.UPCOMING_STATUS);
    CommonResponse response =
        CommonResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).build();
    if (booking != null) {
      String customerId = booking.getCustomerId();
      Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
      if (optionalCustomer.isPresent()) {
        Optional<Flight> optionalFlight = flightRepository.findById(booking.getFlightId());
        if (optionalFlight.isPresent()) {
          Flight flight = optionalFlight.get();
          Timestamp departureTs = flight.getDepartureTs();
          if (departureTs.getTime() - System.currentTimeMillis() > 21600000) {
            booking.setStatus(CANCELLED_STATUS);
            bookingRepository.save(booking);
            String passengerIds = booking.getPassengerIds();
            String[] pIds = passengerIds.split(COMMA_REGEX);
            for (String id : pIds) {
              Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
              if (optionalPassenger.isPresent()) {
                Passenger passenger = optionalPassenger.get();
                addSeat(passenger.getSeatNumber(), passenger.getSeatClass(), flight);
              }
            }
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(Collections.singletonList("Booking canceled successfully"));
            Customer customer = optionalCustomer.get();
            customer.setRewards(booking.getAmount() + booking.getRewardsUsed());
            customerRepository.save(customer);
            String message =
                new StringBuilder()
                    .append("Booking was cancelled for customer ")
                    .append(customer.getFirstName())
                    .append(" ")
                    .append(customer.getLastName())
                    .append(" with id ")
                    .append(booking.getId())
                    .toString();
            emailService.sendConfirmationMail(
                customer.getEmail(), message, "Booking Cancelled", customer.getFirstName());
          } else {
            response.setError(
                ErrResponse.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .description(
                        "Booking cannot be cancelled when departure timestamp is less than 6hrs.")
                    .build());
          }
        }
      } else {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .description("Unauthorized customer found")
                .build());
      }
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.NOT_FOUND.value())
              .description(
                  "Upcoming flight Booking not found. (Either flight has completed its trip or booking does not exits.)")
              .build());
    }
    return response;
  }

  private Double rewardCalculation(Boolean useRewards, Customer customer, double totalAmount) {
    Double customerRewards = customer.getRewards();
    if (Boolean.TRUE.equals(useRewards)) {
      double max = Math.max(customerRewards, totalAmount);
      if (max == totalAmount) {
        totalAmount -= customerRewards;
        customerRewards = 0.0;
      } else {
        customerRewards -= totalAmount;
        totalAmount = 0;
      }
    }
    customer.setRewards(customerRewards);
    customerRepository.save(customer);
    return totalAmount;
  }

  private Double getEconomyPrice(
      Airport sourceAirport, Airport destinationAirport, Double basePrice) {
    double distance =
        AirlineUtils.distance(
            Double.parseDouble(sourceAirport.getLatitude()),
            Double.parseDouble(sourceAirport.getLongitude()),
            Double.parseDouble(destinationAirport.getLatitude()),
            Double.parseDouble(destinationAirport.getLongitude()),
            'k');
    return ((distance / 1000) * basePrice) + AirlineUtils.getRandomNumber(1, 15);
  }

  @Scheduled(cron = "0 */10 * ? * *")
  private void distributeRewards() {
    log.info("Calculating Rewards");
    ArrayList<Flight> completed =
        flightRepository.findByStatusAndDepartureTsLessThan(
            Constant.UPCOMING_STATUS, new Timestamp(System.currentTimeMillis()));
    for (Flight flight : completed) {
      flight.setStatus(Constant.COMPLETED_STATUS);
      ArrayList<Booking> bookings = bookingRepository.findAllByFlightId(flight.getId());
      for (Booking booking : bookings) {
        booking.setStatus(Constant.COMPLETED_STATUS);
        String customerId = booking.getCustomerId();
        Optional<Customer> byId = customerRepository.findById(customerId);
        if (byId.isPresent()) {
          Customer customer = byId.get();
          Double customerRewards = customer.getRewards();
          customerRewards += booking.getAmount() / 20;
          customer.setRewards(customerRewards);
          customerRepository.save(customer);
          String message =
              "Ladies and gentlemen thank you for flying HT airlines, we hope you enjoyed your flight";
          emailService.sendConfirmationMail(
              customer.getEmail(), message, "Thanks for flying with us.", customer.getFirstName());
        }
        bookingRepository.save(booking);
      }
    }
    flightRepository.saveAll(completed);
  }
}
