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
@Schema(
    name = "ChangePasswordRequest",
    description = "schema used to send change password request.")
public class ChangePasswordRequest {

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;

  @NotBlank(message = "Old password cannot be null or empty")
  @Schema(description = "This is the old password.", required = true)
  private String oldPassword;

  @NotBlank(message = "New password cannot be null or empty")
  @Schema(description = "This is the new password.", required = true)
  private String newPassword;
}
