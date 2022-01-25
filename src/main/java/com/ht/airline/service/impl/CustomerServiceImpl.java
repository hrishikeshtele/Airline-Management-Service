package com.ht.airline.service.impl;

import com.ht.airline.db.entity.Customer;
import com.ht.airline.db.repository.CustomerRepository;
import com.ht.airline.dto.common.PasswordDeliveryDTO;
import com.ht.airline.dto.request.ChangePasswordRequest;
import com.ht.airline.dto.request.ForgotPasswordRequest;
import com.ht.airline.dto.request.SignInRequest;
import com.ht.airline.dto.request.SignUpRequest;
import com.ht.airline.dto.response.*;
import com.ht.airline.security.RandomStringGenerator;
import com.ht.airline.service.CustomerService;
import com.ht.airline.service.EmailService;
import java.util.Collections;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired private EmailService emailService;
  @Autowired private RandomStringGenerator randomStringGenerator;
  @Autowired private CustomerRepository customerRepository;

  @Override
  public GenResponse<SignUpResponse> signUp(SignUpRequest req) {

    GenResponse<SignUpResponse> genResponse = new GenResponse<>();

    if (customerRepository.findByEmail(req.getEmail()) != null) {
      genResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
      genResponse.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.BAD_REQUEST.value())
              .description("User already signed up")
              .build());
      return genResponse;
    }
    // generating nonce,password and secure password using nonce and hash algorithm.
    String password = randomStringGenerator.generateRandomPassword();
    String securePassword = randomStringGenerator.createSecurePassword(password);
    Customer customer =
        Customer.builder()
            .email(req.getEmail().toLowerCase(Locale.ROOT))
            .mobileNumber(req.getMobileNumber())
            .firstName(req.getFirstName())
            .lastName(req.getLastName())
            .pwdHash(securePassword)
            .rewards(0.0)
            .admin(false)
            .build();
    // sending generated access code to consumer
    boolean sendPasswordFlag =
        emailService.sendCode(
            PasswordDeliveryDTO.builder()
                .toEmail(req.getEmail())
                .Password(password)
                .name(customer.getFirstName())
                .build(),
            genResponse);
    if (sendPasswordFlag) {
      genResponse.setStatusCode(HttpStatus.OK.value());
      genResponse.setData(
          Collections.singletonList(
              SignUpResponse.builder().message("Sign up successful").build()));
      customerRepository.save(customer);
    }
    return genResponse;
  }

  @Override
  public GenResponse<ChangePasswordResponse> changePassword(ChangePasswordRequest req) {
    GenResponse<ChangePasswordResponse> response =
        GenResponse.<ChangePasswordResponse>builder()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .build();
    Customer customer = customerRepository.findByEmail(req.getEmail().toLowerCase(Locale.ROOT));
    if (customer != null) {
      Customer pc = customerRepository.findByEmailAndPwdHash(req.getEmail(), req.getOldPassword());
      if (pc == null) {
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .description("Incorrect old password")
                .build());
      } else {
        pc.setPwdHash(req.getNewPassword());
        customerRepository.save(pc);
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(
            Collections.singletonList(
                ChangePasswordResponse.builder().message("Password updated").build()));
      }
    } else {
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.UNAUTHORIZED.value())
              .description("Invalid email")
              .build());
    }
    return response;
  }

  @Override
  public GenResponse<SignInResponse> signIn(SignInRequest req) {
    GenResponse<SignInResponse> response = GenResponse.<SignInResponse>builder().build();
    Customer customer = customerRepository.findByEmail(req.getEmail().toLowerCase(Locale.ROOT));
    if (customer != null) {
      Customer sc = customerRepository.findByEmailAndPwdHash(req.getEmail(), req.getPassword());
      if (sc == null) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setError(
            ErrResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .description("Incorrect password")
                .build());
      } else {
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(
            Collections.singletonList(
                SignInResponse.builder()
                    .message("Sign in successful")
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .mobileNumber(customer.getMobileNumber())
                    .rewards(String.format("%.2f", customer.getRewards()))
                    .isAdmin(customer.getAdmin())
                    .build()));
      }
    } else {
      response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
      response.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.UNAUTHORIZED.value())
              .description("Invalid email")
              .build());
    }
    return response;
  }

  @Override
  public GenResponse<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest req) {
    GenResponse<ForgotPasswordResponse> genResponse = new GenResponse<>();
    // generating nonce,password and secure password using nonce and hash algorithm.
    Customer customer = customerRepository.findByEmail(req.getEmail().toLowerCase(Locale.ROOT));
    if (customer != null) {
      String password = randomStringGenerator.generateRandomPassword();
      String securePassword = randomStringGenerator.createSecurePassword(password);
      Customer fc = customerRepository.findByEmail(req.getEmail().toLowerCase(Locale.ROOT));
      fc.setPwdHash(securePassword);
      customerRepository.save(fc);
      // sending generated access code to consumer
      boolean sendPasswordFlag =
          emailService.sendCode(
              PasswordDeliveryDTO.builder().toEmail(req.getEmail()).Password(password).build(),
              genResponse);

      if (sendPasswordFlag) {
        genResponse.setStatusCode(HttpStatus.OK.value());
        genResponse.setData(
            Collections.singletonList(
                ForgotPasswordResponse.builder()
                    .message("Verification code sent successfully")
                    .build()));
      }
    } else {
      genResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
      genResponse.setError(
          ErrResponse.builder()
              .errorCode(HttpStatus.UNAUTHORIZED.value())
              .description("Invalid email")
              .build());
    }
    return genResponse;
  }
}
