package com.enigma.loan.dto.request;

import com.enigma.loan.utils.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserRequest {

    @NotBlank(message = "nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "email tidak boleh kosong")
    @Email
    private String email;

    @NotBlank(message = "Password Tidak Boleh Kosong")
    private String password;

    @NotBlank(message = "Nomor HP Tidak Boleh Kosong")
    private String phoneNumber;

    @NotBlank
    private String role;
}
