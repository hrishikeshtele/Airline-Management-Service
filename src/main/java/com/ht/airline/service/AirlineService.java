package com.ht.airline.service;

import com.ht.airline.dto.request.*;
import com.ht.airline.dto.response.*;

public interface AirlineService {

  ListAirportResponse getAirports();

  GenFlightResponse createFlight(CreateFlightRequest req);

  GenFlightResponse getFlight(String flightId);

  GenFlightResponse updateFlight(UpdateFlightRequest req);

  CommonResponse deleteFlight(DeleteFlightRequest req);

  CommonResponse generateFlights();

  SearchFlightResponse searchFlights(SearchFlightRequest req);

  GenBookFlightResponse bookFlight(BookFlightRequest req);

  GenBookFlightResponse selectSeat(SelectSeatRequest req);

  ListBookingsResponse listBookedFlights(ListBookFlightRequest req);

  CommonResponse cancelBookedFlight(DeleteBookedFlightRequest req);
}
