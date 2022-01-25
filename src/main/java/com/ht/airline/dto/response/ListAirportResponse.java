package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "ListAirportResponse",
    description = "After successful request, the client would get list of airport.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListAirportResponse {

  @Schema(description = "status code", required = true)
  public int statusCode;

  @Schema(description = "error response")
  public ErrResponse error;

  @Schema(description = "List of Airports")
  private List<AirportResponse> data;
}
