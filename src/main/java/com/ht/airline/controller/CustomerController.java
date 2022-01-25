package com.ht.airline.controller;

import static com.ht.airline.common.Examples.*;
import static com.ht.airline.common.UrlConstant.*;

import com.ht.airline.dto.request.ChangePasswordRequest;
import com.ht.airline.dto.request.ForgotPasswordRequest;
import com.ht.airline.dto.request.SignInRequest;
import com.ht.airline.dto.request.SignUpRequest;
import com.ht.airline.dto.response.*;
import com.ht.airline.exception.ServiceException;
import com.ht.airline.service.CustomerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Airline Customer API",
            version = "1.0",
            description =
                "Collection of APIs to be used by customers of HT Airline to perform sign-up / sign-in and other on-boarding related activities. Please make sure that the client is registered with the APi Management platform before consuming the APIs.",
            license = @License(),
            contact =
                @Contact(
                    name = "HT Inc",
                    url = "https://www.hps.com/",
                    email = "hrishikeshtele@gmail.com"),
            termsOfService = "https://www.hps.com/terms-and-conditions/"))
@RestController
@RequestMapping("/" + CUSTOMER_URL)
@Validated
@CrossOrigin(origins = "*")
@Scope("request")
@Slf4j
public class CustomerController {

  @Autowired private CustomerService customerService;

  @PostMapping(SIGN_UP_URL)
  @Tag(name = "customer-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Customer sign-up",
      operationId = "post-signup",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class),
                    examples =
                        @ExampleObject(name = "Customer sign up response", value = SIGN_UP_RES))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description =
          "First API to be used for on-boarding a signup on HT Airline platform. There are certain prerequisites needs to be fulfilled before processing the request. Prerequisites includes (but not limited to) whitelisting of the Customers who are being onboarded to the platform. System generated password would be sent via mail/web-app number")
  public @ApiResponse(
      description = "After successful on-boarding, the client get verification code")
  ResponseEntity<GenResponse<SignUpResponse>> signUpCustomer(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Customer details needs to be passed here.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SignUpRequest.class),
                      examples =
                          @ExampleObject(name = "Customer sign up request", value = SIGN_UP_REQ)))
          @RequestBody
          @Valid
          SignUpRequest request) {
    try {
      GenResponse<SignUpResponse> signUpResGenResponse = customerService.signUp(request);
      return ResponseEntity.status(signUpResGenResponse.getStatusCode()).body(signUpResGenResponse);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(SIGN_IN_URL)
  @Tag(name = "customer-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Customer sign-in",
      operationId = "post-sign-in",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class),
                    examples = {@ExampleObject(name = "Customer sign in", value = SIGN_IN_RES)})),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
      },
      description = "API to be used to sign in on HT platform.")
  public @ApiResponse(
      description =
          "After successful sign in, the client would get status code, message and customer details")
  ResponseEntity<GenResponse<SignInResponse>> signInCustomer(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "email and password needs to be passed.",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = SignInRequest.class),
                      examples =
                          @ExampleObject(name = "Customer sign in request", value = SIGN_IN_REQ)))
          @RequestBody
          @Valid
          SignInRequest request) {
    try {
      GenResponse<SignInResponse> signUpResGenResponse = customerService.signIn(request);
      return ResponseEntity.status(signUpResGenResponse.getStatusCode()).body(signUpResGenResponse);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(CHANGE_PASSWORD_URL)
  @Tag(name = "customer-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Set Password for a given Customer",
      operationId = "post-Customer-change-password",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class),
                    examples = {
                      @ExampleObject(
                          name = "Customer change password response",
                          value = CHANGE_PWD_RES),
                      @ExampleObject(
                          name = "Customer change password after forgot password response",
                          value = "{}")
                    })),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description = "Used to set the password of the Customer.")
  public @ApiResponse(
      description = "After successful change password, the client would get success message")
  ResponseEntity<GenResponse<ChangePasswordResponse>> changePassword(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Client should send old,new password and email",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ChangePasswordRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Customer change password request",
                              value = CHANGE_PWD_REQ)))
          @RequestBody
          @Valid
          ChangePasswordRequest request) {
    try {
      GenResponse<ChangePasswordResponse> genResponse = customerService.changePassword(request);
      return ResponseEntity.status(genResponse.getStatusCode()).body(genResponse);
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping(FORGOT_PASSWORD_URL)
  @Tag(name = "customer-api", description = "This API is exposed to web-app")
  @Operation(
      summary = "Request new Password by calling this api",
      operationId = "post-customer-forgot-Password",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class),
                    examples =
                        @ExampleObject(
                            name = "Customer forgot password response",
                            value = FORGOT_PWD_RES))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenResponse.class)))
      },
      description =
          "If the Customer forgets the password, this API can be used to reset the password.")
  public @ApiResponse(
      description = "After successful request, the client would get new verification code")
  ResponseEntity<GenResponse<ForgotPasswordResponse>> forgotPassword(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Email is required.",
              required = true,
              content =
                  @Content(
                      schema = @Schema(implementation = ForgotPasswordRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Customer forgot password request",
                              value = FORGOT_PWD_REQ)))
          @RequestBody
          @Valid
          ForgotPasswordRequest request) {
    try {
      GenResponse<ForgotPasswordResponse> forgotPasswordResGenResponse =
          customerService.forgotPassword(request);
      return ResponseEntity.status(forgotPasswordResGenResponse.getStatusCode())
          .body(forgotPasswordResGenResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
