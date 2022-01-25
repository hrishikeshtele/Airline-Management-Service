package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "AirportResponse",
    description = "After successful request, the client would get list of airports")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirportResponse {

  @Schema(description = "id", required = true)
  private String id;

  @Schema(description = "city", required = true)
  private String city;

  @Schema(description = "city code", required = true)
  private String cityCode;

  @Schema(description = "airport name", required = true)
  private String airportName;
}
