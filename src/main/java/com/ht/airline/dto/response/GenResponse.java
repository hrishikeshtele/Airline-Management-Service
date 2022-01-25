package com.ht.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "Gen Response",
    description =
        "After request is made, the client would get appropriate details based on the request.")
public class GenResponse<T> {

  @Schema(description = "status code", required = true)
  private int statusCode;

  @Schema(description = "Data")
  private List<T> data;

  @Schema(description = "error response")
  private ErrResponse error;
}
