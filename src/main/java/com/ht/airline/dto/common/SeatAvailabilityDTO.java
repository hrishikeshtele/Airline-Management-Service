package com.ht.airline.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "SeatAvailability", description = "Seat availability of flight")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatAvailabilityDTO {
  @Schema(name = "Economy Seats", required = true)
  List<String> economySeats;

  @Schema(name = "Premium Seats", required = true)
  List<String> premiumSeats;

  @Schema(name = "Business Seats", required = true)
  List<String> businessSeats;
}
