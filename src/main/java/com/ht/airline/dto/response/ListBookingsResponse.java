package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "ListBookingsResponse",
    description = "After successful request, the client would get list of booked flights.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListBookingsResponse {

  @Schema(description = "status code", required = true)
  public int statusCode;

  @Schema(description = "error response")
  public ErrResponse error;

  @NotNull(message = "Bookings cannot be null")
  @Schema(name = "List of Bookings", required = true)
  private List<ListBookFlightResponse> data;
}
