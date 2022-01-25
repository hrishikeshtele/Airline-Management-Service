package com.ht.airline.service;

import com.ht.airline.dto.common.PasswordDeliveryDTO;
import com.ht.airline.dto.response.GenResponse;

public interface EmailService {

  boolean sendCode(PasswordDeliveryDTO passwordDeliveryDTO, GenResponse genResponse);

  boolean sendConfirmationMail(String email, String mess, String messStatus, String name);
}
