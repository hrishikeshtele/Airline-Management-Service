package com.ht.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SearchFlightRequest", description = "schema used to search flight.")
public class SearchFlightRequest {

  @Size(max = 50, message = "Invalid source airport id")
  @NotBlank(message = "Source Airport Id cannot be null or empty")
  @Schema(description = "Source Airport Id", required = true)
  private String sourceAirportId;

  @Size(max = 50, message = "Invalid destination airport id")
  @NotBlank(message = "Destination Airport Id cannot be null or empty")
  @Schema(name = "Destination Airport Id", required = true)
  private String destinationAirportId;

  @NotNull(message = "departure timestamp cannot be null")
  @Schema(name = "departure_timestamp", required = true)
  private Timestamp departureTs;
}
