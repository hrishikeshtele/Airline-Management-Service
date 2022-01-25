package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "SignInResponse",
    description = "After successful sign in, the client would get success message.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse extends BaseDTO {

  @Schema(description = "message")
  private String message;

  @Schema(description = "first name")
  private String firstName;

  @Schema(description = "last name")
  private String lastName;

  @Schema(description = "mobile number")
  private String mobileNumber;

  @Schema(description = "Rewards")
  private String rewards;

  @Schema(description = "Is admin")
  private Boolean isAdmin;
}
