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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "ErrorResponse",
    description =
        "After request is made, the client would get appropriate details based on the request.")
public class ErrResponse extends BaseDTO {

  @Schema(description = "error code", required = true)
  private Integer errorCode;

  @Schema(description = "description", required = true)
  private String description;
}
