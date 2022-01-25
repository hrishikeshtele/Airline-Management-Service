package com.ht.airline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DeleteFlightRequest", description = "schema used to delete flight.")
public class DeleteFlightRequest {

  @NotBlank(message = "EMAIL_CANNOT_BE_NULL_OR_EMPTY")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;

  @NotBlank(message = "Flight Id cannot be null or empty")
  @Schema(description = "Flight Id", required = true)
  private String flightId;
}
