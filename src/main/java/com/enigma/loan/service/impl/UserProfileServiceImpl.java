package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.dto.response.UserProfileResponse;
import com.enigma.loan.entity.User;
import com.enigma.loan.entity.UserProfile;
import com.enigma.loan.mapper.UserProfileMapper;
import com.enigma.loan.repository.FileStorageRepository;
import com.enigma.loan.repository.UserProfileRepository;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.service.FileStorageService;
import com.enigma.loan.service.UserProfileService;
import com.enigma.loan.utils.exception.DuplicateResourceException;
import com.enigma.loan.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final FileStorageRepository fileStorageRepository;
    private final FileStorageService fileStorageService;

    @Override
    public UserProfileResponse create(UserProfileRequest request) {

        User user = getCurrentUser();

        if (userProfileRepository.existsByUser(user)) {
            throw new DuplicateResourceException(
                    "User profile already exists"
            );
        }

        UserProfile userProfile =
                UserProfileMapper.convertToUserProfile(request);

        userProfile.setUser(user);

        UserProfile savedProfile =
                userProfileRepository.save(userProfile);

        return UserProfileMapper.convertToUserProfileResponse(savedProfile);
    }

    @Override
    public UserProfileResponse get() {

        User user = getCurrentUser();

        UserProfile userProfile = findByUser(user);

        return UserProfileMapper.convertToUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse update(UserProfileRequest request) {

        User user = getCurrentUser();

        UserProfile userProfile = findByUser(user);

        userProfile.setMonthlyIncome(request.getMonthlyIncome());
        userProfile.setMonthlyExpense(request.getMonthlyExpense());
        userProfile.setEmploymentStatus(request.getEmploymentStatus());

        UserProfile updatedProfile =
                userProfileRepository.save(userProfile);

        return UserProfileMapper.convertToUserProfileResponse(updatedProfile);
    }

    @Override
    public UserProfileResponse uploadKtp(MultipartFile file) {
        return null;
    }

    @Override
    public FileResponse uploadSalarySlip(MultipartFile file) {
        return null;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }

    private UserProfile findByUser(User user) {
        return userProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User profile not found"
                        )
                );
    }
}
