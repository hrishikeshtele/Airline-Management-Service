package com.ht.airline.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDTO extends BaseDTO {

  @NotBlank(message = "First name cannot be null or empty")
  @Schema(name = "First name", required = true)
  private String firstName;

  @NotBlank(message = "Last name cannot be null or empty")
  @Schema(name = "Last name", required = true)
  private String lastName;

  @NotBlank(message = "MOBILE_NUMBER_CANNOT_BE_NULL_OR_EMPTY")
  @Schema(description = "Consumer mobile number", required = true)
  @Pattern(
      regexp = "^\\+[1-9]\\d{6,14}$",
      message = "PLEASE_ENTER_VALID_MOBILE_NUMBER_IN_E_164_FORMAT")
  private String mobileNumber;

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(name = "Email", required = true)
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Seat class cannot be null or blank")
  @Schema(name = "seat class", required = true)
  @Pattern(regexp = "E|P|B")
  private String seatClass;

  @NotBlank(message = "Seat number be null or blank")
  @Schema(name = "seat number", required = true)
  private String seatNumber;
}
