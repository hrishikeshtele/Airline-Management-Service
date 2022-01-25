package com.ht.airline.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatPassengerDTO extends BaseDTO {

  @NotNull(message = "Passenger id cannot be null or empty.")
  @Schema(name = "Passenger id", required = true)
  private String id;

  @NotNull(message = "Seat class cannot be null")
  @Schema(name = "seat class", required = true)
  @Pattern(regexp = "E|P|B")
  private String seatClass;

  @NotNull(message = "Seat number be null")
  @Schema(name = "seat number", required = true)
  private String seatNumber;
}
