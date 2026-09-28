package com.enigma.loan.service;

import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);

    List<UserResponse> getAll();

    UserResponse getById(Long id);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}