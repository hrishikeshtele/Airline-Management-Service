package com.ht.airline.dto.request;

import com.ht.airline.dto.common.PassengerDTO;
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
@Schema(name = "BookFlightRequest", description = "schema used to book flight.")
public class BookFlightRequest {

  @NotBlank(message = "Source Airport Id cannot be null or empty")
  @Schema(description = "Source Airport Id", required = true)
  private String flightId;

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Email", required = true)
  private String email;

  @NotNull(message = "reward flag cannot be null or empty")
  @Schema(name = "Reward flag", required = true)
  private Boolean rewards;

  @NotNull(message = "Passenger details cannot be null")
  @Schema(name = "Passengers", required = true)
  private ArrayList<PassengerDTO> passengers;
}
