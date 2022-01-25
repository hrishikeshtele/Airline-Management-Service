package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.SeatAvailabilityDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "FlightResponse",
    description = "After successful request, the client would get list of available flights")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightResponse {

  @Schema(description = "id", required = true)
  private String id;

  @Schema(name = "Source Airport Id", required = true)
  private String sourceAirportId;

  @Schema(name = "Destination Airport Id", required = true)
  private String destinationAirportId;

  @Schema(name = "Air Carrier id", required = true)
  private String airCarrierId;

  @Schema(name = "Departure Timestamp", required = true)
  private Timestamp departureTs;

  @Schema(name = "Arrival Timestamp", required = true)
  private Timestamp arrivalTs;

  @Schema(name = "Economy Price", required = true)
  private String economyPrice;

  @Schema(name = "Premium Economy Price", required = true)
  private String premiumEconomyPrice;

  @Schema(name = "Business Price", required = true)
  private String businessPrice;

  @Schema(name = "Flight status", required = true)
  private String status;

  @Schema(name = "Seat availability", required = true)
  private SeatAvailabilityDTO seatAvailability;
}
