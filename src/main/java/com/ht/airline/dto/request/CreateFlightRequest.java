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
@Schema(name = "CreateFlightRequest", description = "schema used to create flight.")
public class CreateFlightRequest {

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;

  @NotBlank(message = "Air Carrier Id cannot be null or empty")
  @Schema(description = "Air Carrier Id", required = true)
  private String airCarrierId;

  @NotBlank(message = "Source Airport Id cannot be null or empty")
  @Schema(description = "Source Airport Id", required = true)
  private String sourceAirportId;

  @NotBlank(message = "Destination Airport Id cannot be null or empty")
  @Schema(description = "Destination Airport Id", required = true)
  private String destinationAirportId;

  @NotBlank(message = "Departure timestamp cannot be null or empty")
  @Schema(description = "Departure timestamp", required = true)
  private String departureTs;

  @NotBlank(message = "Arrival timestamp cannot be null or empty")
  @Schema(description = "Arrival timestamp", required = true)
  private String arrivalTs;
}
