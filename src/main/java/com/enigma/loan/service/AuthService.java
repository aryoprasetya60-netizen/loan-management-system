package com.enigma.loan.service;


import com.enigma.loan.dto.request.LoginRequest;
import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.LoginResponse;
import com.enigma.loan.dto.response.UserResponse;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthService {
    LoginResponse loginUser(LoginRequest request) throws BadCredentialsException;

    UserResponse registerUser(UserRequest request);
}
