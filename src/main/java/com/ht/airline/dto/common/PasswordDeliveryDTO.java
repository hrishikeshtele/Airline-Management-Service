package com.ht.airline.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordDeliveryDTO extends BaseDTO {

  private String toEmail;

  private String Password;

  private String name;
}
