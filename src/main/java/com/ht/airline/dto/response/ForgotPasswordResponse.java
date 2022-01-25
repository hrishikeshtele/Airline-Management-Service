package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ht.airline.dto.common.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "ForgotPasswordResponse",
    description =
        "After successful forgot password request, the client would get nonce and reference id")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForgotPasswordResponse extends BaseDTO {

  @Schema(description = "message", required = true)
  private String message;
}
