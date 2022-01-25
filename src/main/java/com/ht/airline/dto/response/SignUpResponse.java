package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "SignupResponse",
    description = "After successful on-boarding, the client would response and email.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse extends BaseDTO {

  @Schema(description = "message", required = true)
  private String message;
}
