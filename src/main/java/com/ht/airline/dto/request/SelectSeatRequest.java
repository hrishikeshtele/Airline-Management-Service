package com.ht.airline.dto.request;

import com.ht.airline.dto.common.SeatPassengerDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SelectSeatRequest", description = "schema used to select/update seat of booking.")
public class SelectSeatRequest {

  @NotBlank(message = "Booking Id cannot be null or empty")
  @Schema(description = "Booking Id", required = true)
  private String bookingId;

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Email", required = true)
  private String email;

  @NotNull(message = "Seat Passenger details cannot be null")
  @Schema(name = "SeatPassengerDTO", required = true)
  private ArrayList<SeatPassengerDTO> passengers;
}
