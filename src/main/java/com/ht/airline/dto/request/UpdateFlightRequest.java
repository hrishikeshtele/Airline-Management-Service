package com.ht.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UpdateFlightRequest", description = "schema used to update flight.")
public class UpdateFlightRequest {

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;

  @NotBlank(message = "Flight Id cannot be null or empty")
  @Schema(description = "Flight Id", required = true)
  private String flightId;

  @Schema(description = "Air Carrier Id")
  private String airCarrierId;

  @Schema(description = "Source Airport Id")
  private String sourceAirportId;

  @Schema(description = "Destination Airport Id")
  private String destinationAirportId;

  @Schema(description = "Departure Timestamp")
  private String departureTs;

  @Schema(description = "Arrival Timestamp")
  private String arrivalTs;

  @Schema(description = "Economy Price")
  private Double economyPrice;

  @Schema(description = "Premium Economy Price")
  private Double premiumEconomyPrice;

  @Schema(description = "Business price")
  private Double businessPrice;
}
