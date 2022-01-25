package com.ht.airline.controller;

import static com.ht.airline.common.Examples.*;
import static com.ht.airline.common.UrlConstant.*;

import com.ht.airline.dto.request.*;
import com.ht.airline.dto.response.*;
import com.ht.airline.exception.ServiceException;
import com.ht.airline.service.AirlineService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Airline API",
            version = "1.0",
            description =
                "Collection of APIs to be used by customers of HT Airline to search and book flight.",
            license = @License(),
            contact =
                @Contact(
                    name = "HT Inc",
                    url = "https://www.hps.com/",
                    email = "hrishikeshtele@gmail.com"),
            termsOfService = "https://www.hps.com/terms-and-conditions/"))
@RestController
@RequestMapping("/" + AIRPORT_URL)
@Validated
@CrossOrigin(origins = "*")
@Scope("request")
@Slf4j
public class AirlineController {

  @Autowired private AirlineService airlineService;

  @GetMapping(LIST_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Airport list",
      operationId = "get-list",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListAirportResponse.class),
                    examples =
                        @ExampleObject(
                            name = "List of airports response",
                            value = LIST_AIRPORT_SUCCESS_RES))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API used to list all available airports.")
  public @ApiResponse(
      description = "After successful request, the client get list of available airports.")
  ResponseEntity<ListAirportResponse> listAirports() {
    try {
      ListAirportResponse response = airlineService.getAirports();
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(SEARCH_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Search flight",
      operationId = "post-search",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SearchFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Search flight response", value = SEARCH_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SearchFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to search flights on HT platform.")
  public @ApiResponse(
      description =
          "After successful search, the client would get available flights from source and destination")
  ResponseEntity<SearchFlightResponse> searchFlights(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Source airport and destination airport id along with departure.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SearchFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Search flight request",
                              value = SEARCH_FLIGHT_REQ)))
          @RequestBody
          @Valid
          SearchFlightRequest request) {
    try {
      SearchFlightResponse response = airlineService.searchFlights(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(FLIGHT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to create flight")
  @Operation(
      summary = "Create flight",
      operationId = "post-create-flight",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Create flight response", value = GENERIC_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to create flight on HT platform.")
  public @ApiResponse(
      description =
          "After successful create request, the client would get available flights from source and destination")
  ResponseEntity<GenFlightResponse> createFlight(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Source airport and destination airport id along with departure.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Create flight request",
                              value = CREATE_FLIGHT_REQ)))
          @RequestBody
          @Valid
          CreateFlightRequest request) {
    try {
      GenFlightResponse response = airlineService.createFlight(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping(FLIGHT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to get flight")
  @Operation(
      summary = "Get flight details",
      operationId = "get-flight",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Update Response", value = GENERIC_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to get flight on HT platform.")
  public @ApiResponse(
      description = "After successful get request, the client would get flight response.")
  ResponseEntity<GenFlightResponse> getFlight(@NotNull @RequestParam String flightId) {
    try {
      GenFlightResponse response = airlineService.getFlight(flightId);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PutMapping(FLIGHT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to update flight")
  @Operation(
      summary = "Update flight",
      operationId = "put-update-flight",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Update Response", value = GENERIC_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to update flight on HT platform.")
  public @ApiResponse(
      description = "After successful update request, the client would get flight response.")
  ResponseEntity<GenFlightResponse> updateFlight(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Source airport and destination airport id along with departure.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UpdateFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Update flight request",
                              value = UPDATE_FLIGHT_REQ)))
          @RequestBody
          @Valid
          UpdateFlightRequest request) {
    try {
      GenFlightResponse response = airlineService.updateFlight(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping(FLIGHT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to update flight")
  @Operation(
      summary = "Delete flight",
      operationId = "delete-flight",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommonResponse.class),
                    examples = {
                      @ExampleObject(name = "Delete Response", value = DELETE_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to update flight on HT platform.")
  public @ApiResponse(
      description = "After successful delete request, the client would get success response.")
  ResponseEntity<CommonResponse> deleteFlight(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Source airport and destination airport id along with departure.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = DeleteFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Delete flight request",
                              value = DELETE_FLIGHT_REQ)))
          @RequestBody
          @Valid
          DeleteFlightRequest request) {
    try {
      CommonResponse response = airlineService.deleteFlight(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(BOOK_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Book flight for customer",
      operationId = "post-book",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenBookFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Book flight response", value = BOOK_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenBookFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to book flight for the Customer.")
  public @ApiResponse(
      description = "After successful booking flight, the client would get success message")
  ResponseEntity<GenBookFlightResponse> bookFlight(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Client should send flight and passenger details.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = BookFlightRequest.class),
                      examples =
                          @ExampleObject(name = "Book Flight request", value = BOOK_FLIGHT_REQ)))
          @RequestBody
          @Valid
          BookFlightRequest request) {
    try {
      GenBookFlightResponse response = airlineService.bookFlight(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(BOOK_SEAT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Select seat for booked flight for customer",
      operationId = "post-book-seat",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenBookFlightResponse.class),
                    examples = {
                      @ExampleObject(name = "Book flight response", value = SELECT_SEAT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenBookFlightResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to book flight for the Customer.")
  public @ApiResponse(
      description = "After successful booking flight, the client would get success message")
  ResponseEntity<GenBookFlightResponse> selectSeat(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Client should send flight and passenger details.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SelectSeatRequest.class),
                      examples =
                          @ExampleObject(name = "Select seat request", value = SELECT_SEAT_REQ)))
          @RequestBody
          @Valid
          SelectSeatRequest request) {
    try {
      GenBookFlightResponse response = airlineService.selectSeat(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping(BOOK_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Delete booked flight for customer",
      operationId = "delete-book",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class),
                    examples = {
                      @ExampleObject(name = "Delete booking response", value = DELETE_BOOKING_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to book flight for the Customer.")
  public @ApiResponse(
      description = "After successful booking flight, the client would get success message")
  ResponseEntity<CommonResponse> deleteBookedFlight(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Client should send booking id and email.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = DeleteBookedFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Delete Flight request",
                              value = DELETE_BOOKING_REQ)))
          @RequestBody
          @Valid
          DeleteBookedFlightRequest request) {
    try {
      CommonResponse response = airlineService.cancelBookedFlight(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(BOOK_LIST_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "List Booked flights for customer",
      operationId = "post-book-list",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListBookingsResponse.class),
                    examples = {
                      @ExampleObject(name = "list Booked flight response", value = LIST_BOOKING_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListBookingsResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to get list of bookings for the Customer.")
  public @ApiResponse(
      description = "After successful request, the client would get list of previous bookings")
  ResponseEntity<ListBookingsResponse> listBookedFlights(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Client should send flight and passenger details.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ListBookFlightRequest.class),
                      examples =
                          @ExampleObject(
                              name = "List Book Flight request",
                              value = LIST_BOOKING_REQ)))
          @RequestBody
          @Valid
          ListBookFlightRequest request) {
    try {
      ListBookingsResponse response = airlineService.listBookedFlights(request);
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(GENERATE_FLIGHT_URL)
  @Tag(name = "airport-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Generate flights",
      operationId = "post-generate-flight",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommonResponse.class),
                    examples = {
                      @ExampleObject(name = "Generate flight response", value = GENERATE_FLIGHT_RES)
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CommonResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to book flight for the Customer.")
  public @ApiResponse(
      description = "After successful generation of flights, the client would get success message")
  ResponseEntity<CommonResponse> generateFlights() {
    try {
      CommonResponse response = airlineService.generateFlights();
      return ResponseEntity.status(response.getStatusCode()).body(response);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
