package com.ht.airline.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SignInRequest", description = "schema used to send sign in request.")
public class SignInRequest extends BaseDTO {

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(description = "Customer email address", required = true)
  @Email(message = "Please enter valid email address")
  private String email;

  @Schema(description = "Password", required = true)
  @NotBlank(message = "Password cannot be null or empty")
  private String password;
}
