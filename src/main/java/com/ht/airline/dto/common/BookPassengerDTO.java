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
public class BookPassengerDTO extends BaseDTO {

  @NotBlank(message = "Id cannot be null or empty")
  @Schema(name = "Id", required = true)
  private String id;

  @NotBlank(message = "First name cannot be null or empty")
  @Schema(name = "First name", required = true)
  private String firstName;

  @NotBlank(message = "Last name cannot be null or empty")
  @Schema(name = "Last name", required = true)
  private String lastName;

  @NotBlank(message = "Mobile number cannot be null or empty")
  @Schema(name = "Mobile Number", required = true)
  private String mobileNumber;

  @NotBlank(message = "Email cannot be null or empty")
  @Schema(name = "Email", required = true)
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Seat class cannot be null or empty")
  @Schema(name = "seat class", required = true)
  @Pattern(regexp = "E|P|B")
  private String seatClass;

  @NotBlank(message = "Seat number cannot be null or empty")
  @Schema(name = "seat number", required = true)
  private String seatNumber;

  @NotBlank(message = "PNR cannot be null or empty")
  @Schema(name = "PNR", required = true)
  private String pnr;
}
