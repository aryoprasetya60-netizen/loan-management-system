package com.enigma.loan.dto.response;

import com.enigma.loan.utils.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String phoneNumber;
    private UserRole role;
}