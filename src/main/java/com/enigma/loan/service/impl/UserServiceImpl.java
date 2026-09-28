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

        validateEmailNotExists(request.getEmail());

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

        if (!user.getEmail().equals(request.getEmail())) {
            validateEmailNotExists(request.getEmail());
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
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
    private void validateEmailNotExists(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "Email Sudah Digunakan: " + email
            );
        }
    }


}