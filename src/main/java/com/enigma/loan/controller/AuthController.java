package com.enigma.loan.controller;

import com.enigma.loan.dto.request.LoginRequest;
import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.CommonResponse;
import com.enigma.loan.dto.response.LoginResponse;
import com.enigma.loan.dto.response.UserResponse;
import com.enigma.loan.service.AuthService;
import com.enigma.loan.utils.ResponseUtil;
import com.enigma.loan.utils.constants.ResponseMessage;
import com.enigma.loan.utils.validators.ValidationGroups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description = "Auth endpoint")
public class AuthController {
    private final AuthService authService;
    // register user baru
    @Operation(
        summary = "Register new User",
            description = "ini adalah endpoint registreasi user baru",
            responses = {
                @ApiResponse(responseCode = "200",
                description = "berhasil melakukan registrasi")
    })
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserResponse>> registerUser(
            @Validated(ValidationGroups.OnCreate.class)
            @RequestBody UserRequest request) {
        UserResponse userResponse = authService.registerUser(request);

        return ResponseUtil.buildResponse(HttpStatus.CREATED, ResponseMessage.SUCCES_CREATE_DATA, userResponse);
    }

    // login user
    @Operation(
            summary = "Login User",
                description = "endpoint login system",
            responses = {
                    @ApiResponse(responseCode = "200",
                    description = "berhasil login system")
    })
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> loginUser(
            @Valid
            @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.loginUser(request);

        return ResponseUtil.buildResponse(HttpStatus.OK, ResponseMessage.SUCCES_LOGIN, loginResponse);
    }
}
