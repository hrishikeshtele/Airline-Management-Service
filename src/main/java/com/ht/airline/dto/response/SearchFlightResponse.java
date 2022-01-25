package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "SearchFlightResponse",
    description = "After successful request, the client would get details of searched flights.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchFlightResponse {

  @Schema(description = "status code", required = true)
  public int statusCode;

  @Schema(description = "error response")
  public ErrResponse error;

  @Schema(description = "List of flights", required = true)
  private ArrayList<FlightResponse> data;
}
