package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.UserResponse;
import com.enigma.loan.entity.User;
import com.enigma.loan.mapper.UserMapper;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.service.UserService;
import com.enigma.loan.utils.enums.UserRole;
import com.enigma.loan.utils.exception.DuplicateResourceException;
import com.enigma.loan.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {

        validateUsernameNotExists(request.getUsername());

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = UserMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return UserMapper.convertToUserResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAll() {

        List<User> users = userRepository.findAll();

        return UserMapper.convertToUserListResponse(users);
    }

    @Override
    public UserResponse getById(Long id) {

        User user = findById(id);

        return UserMapper.convertToUserResponse(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {

        User user = findById(id);

        if (!user.getUsername().equals(request.getUsername())) {
            validateUsernameNotExists(request.getUsername());
        }

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(UserRole.valueOf(request.getRole()));

        User updatedUser = userRepository.save(user);

        return UserMapper.convertToUserResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {

        User user = findById(id);

        userRepository.delete(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
    }
    private void validateUsernameNotExists(String username) {

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException(
                    "Username Sudah Digunakan: " + username
            );
        }
    }


}