package com.ht.airline.dto.request;

import com.ht.airline.dto.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SignupRequest", description = "schema used to send sign up request.")
public class SignUpRequest extends BaseDTO {

  @Size(max = 50, message = "First name invalid size")
  @NotBlank(message = "first name cannot be null or empty")
  @Schema(description = "Consumer first name", required = true)
  private String firstName;

  @Size(max = 50, message = "Last name invalid size")
  @NotBlank(message = "Last name cannot be null or empty")
  @Schema(description = "Consumer last name", required = true)
  private String lastName;

  @NotBlank(message = "Mobile number cannot be null or empty")
  @Schema(description = "Consumer mobile number", required = true)
  @Pattern(
      regexp = "^\\+[1-9]\\d{6,14}$",
      message = "Please enter valid mobile number in E 1.64 Format.")
  private String mobileNumber;

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;
}
