package com.enigma.loan.mapper;

import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.UserResponse;
import com.enigma.loan.entity.User;
import com.enigma.loan.utils.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    // mapper untuk create
    public static UserResponse convertToUserResponse(User user){
        // 1. instansiate
        UserResponse userResponse = new UserResponse();

        // 2. set value
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    public static User toEntity(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }

    // maper untuk list
    public static List<UserResponse> convertToUserListResponse(List<User> users) {
        return users.stream()
                .map(UserMapper::convertToUserResponse)
                .toList();
    }
}
