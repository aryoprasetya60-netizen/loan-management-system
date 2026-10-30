package com.enigma.loan.config;

import com.enigma.loan.entity.User;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.utils.enums.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initAdminUser() {
        // validasi apakah admin sudah exist
        if(!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .name("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.valueOf("ADMIN"))
                    .build();

            userRepository.save(admin);

            System.out.println("User admin sudah berhasil dibuat dengan \n email: admin@mail.com / admin123");

        }
    }
}
