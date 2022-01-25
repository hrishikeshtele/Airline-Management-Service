package com.ht.airline.dto.response;

import com.ht.airline.dto.common.BookPassengerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListBookFlightResponse {

  @NotBlank(message = "Booking id cannot be null or empty")
  @Schema(name = "Booking Id", required = true)
  private String bookingId;

  @NotBlank(message = "Flight id cannot be null or empty")
  @Schema(name = "Flight Id", required = true)
  private String flightId;

  @NotBlank(message = "Source airport cannot be null or empty")
  @Schema(name = "Source Airport", required = true)
  private String sourceAirport;

  @NotBlank(message = "Destination airport cannot be null or empty")
  @Schema(name = "Destination Airport", required = true)
  private String destinationAirport;

  @NotBlank(message = "Total Amount cannot be null or blank")
  @Schema(name = "Total Amount", required = true)
  private String totalAmount;

  @NotBlank(message = "Status cannot be null or blank")
  @Schema(name = "status", required = true)
  private String status;

  @NotNull(message = "Passenger details cannot be null")
  @Schema(name = "Passengers", required = true)
  private ArrayList<BookPassengerDTO> passengers;
}
