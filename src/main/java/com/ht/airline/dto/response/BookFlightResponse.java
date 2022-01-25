package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.BookPassengerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(
    name = "BookFlightResponse",
    description = "After successful request, the client would get booking details")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookFlightResponse {

  @NotBlank(message = "Booking id cannot be null or empty")
  @Schema(name = "Booking Id", required = true)
  private String bookingId;

  @NotBlank(message = "Message cannot be null or empty")
  @Schema(name = "Message", required = true)
  private String message;

  @NotBlank(message = "Total Amount cannot be null or blank")
  @Schema(name = "Total Amount", required = true)
  private String totalAmount;

  @NotBlank(message = "Passenger details cannot be null or empty")
  @Schema(name = "Passengers", required = true)
  private ArrayList<BookPassengerDTO> passengers;
}
