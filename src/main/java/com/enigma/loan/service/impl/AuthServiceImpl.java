package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.LoginRequest;
import com.enigma.loan.dto.request.UserRequest;
import com.enigma.loan.dto.response.LoginResponse;
import com.enigma.loan.dto.response.UserResponse;
import com.enigma.loan.entity.User;
import com.enigma.loan.mapper.UserMapper;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.security.jwt.JwtUtils;
import com.enigma.loan.service.AuthService;
import com.enigma.loan.utils.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // kebutuhan auth
    private final AuthenticationManager authenticationManager;

    // untuk generate token
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse loginUser(LoginRequest request) throws BadCredentialsException {
        // authenticare menggunakan authenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // set authentication didalam security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate jwt token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // get role
        String role = jwtUtils.getRoleFromJwtToken(jwt);

        // return response
        return LoginResponse.builder()
                .token(jwt)
                .role(role)
                .username(request.getUsername())
                .build();
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserRole userRole = (request.getRole() != null && !request.getRole().isBlank())
                ? UserRole.valueOf(request.getRole())
                : UserRole.CUSTOMER;


        User newUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .role(userRole)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(newUser);
        return UserMapper.convertToUserResponse(savedUser);
    }
}
