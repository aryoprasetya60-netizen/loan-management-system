package com.enigma.loan.mapper;

import com.enigma.loan.dto.response.UserResponse;
import com.enigma.loan.entity.User;

import java.util.List;

public class UserMapper {
    // mapper untuk create
    public static UserResponse convertToUserResponse(User user){
        // 1. instansiate
        UserResponse userResponse = new UserResponse();

        // 2. set value
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    // maper untuk list
    public static List<UserResponse> convertToUserListResponse(List<User> users) {
        return users.stream()
                .map(UserMapper::convertToUserResponse)
                .toList();
    }
}
