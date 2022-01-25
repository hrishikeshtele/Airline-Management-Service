package com.ht.airline.service;

import com.ht.airline.dto.request.ChangePasswordRequest;
import com.ht.airline.dto.request.ForgotPasswordRequest;
import com.ht.airline.dto.request.SignInRequest;
import com.ht.airline.dto.request.SignUpRequest;
import com.ht.airline.dto.response.*;

public interface CustomerService {

  GenResponse<SignUpResponse> signUp(SignUpRequest req);

  GenResponse<ChangePasswordResponse> changePassword(ChangePasswordRequest req);

  GenResponse<SignInResponse> signIn(SignInRequest req);

  GenResponse<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest req);
}
