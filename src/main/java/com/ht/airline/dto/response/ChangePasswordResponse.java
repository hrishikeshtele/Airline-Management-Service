package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
    name = "ChangePasswordResponse",
    description = "After successful change password, the client would get success message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordResponse {

  @Schema(description = "Message")
  private String message;
}
