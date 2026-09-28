package com.enigma.loan.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "email tidak boleh kosong")
    @Email
    private String email;

    @NotBlank(message = "Password Tidak Boleh Kosong")
    private String password;
}
