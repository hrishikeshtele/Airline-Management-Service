package com.ht.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ListBookFlightRequest", description = "schema used to get list of booked flight.")
public class ListBookFlightRequest {

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Email", required = true)
  private String email;
}
