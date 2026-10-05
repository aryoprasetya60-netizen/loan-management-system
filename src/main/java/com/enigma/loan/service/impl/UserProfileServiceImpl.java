package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.dto.response.UserProfileResponse;
import com.enigma.loan.entity.FileStorage;
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
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public UserProfileResponse uploadKtp(MultipartFile file) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile =
                userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        FileStorage savedFile =
                fileStorageService.store(
                        file,
                        "KTP",
                        String.valueOf(userProfile.getId())
                );

        userProfile.setKtpPath(savedFile.getFilePath());

        UserProfile savedProfile =
                userProfileRepository.save(userProfile);

        return UserProfileMapper.convertToUserProfileResponse(savedProfile);
    }

    @Override
    @Transactional
    public UserProfileResponse uploadSalarySlip(MultipartFile file) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile =
                userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        FileStorage savedFile =
                fileStorageService.store(
                        file,
                        "SALARY_SLIP",
                        String.valueOf(userProfile.getId())
                );

        userProfile.setSalarySlipPath(savedFile.getFilePath());

        UserProfile savedProfile =
                userProfileRepository.save(userProfile);

        return UserProfileMapper.convertToUserProfileResponse(savedProfile);
    }

    @Override
    public Resource downloadKtp() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile = userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        if (userProfile.getKtpPath() == null) {
            throw new ResourceNotFoundException("KTP", "user", user.getId());
        }

        FileStorage fileStorage = fileStorageRepository.findByReferenceIdAndCategory(String.valueOf(userProfile.getId()), "KTP")
                        .orElseThrow(() ->
                                new ResourceNotFoundException("KTP file", "user", user.getId()));

        return fileStorageService.loadFileAsResource(
                fileStorage.getId()
        );
    }

    @Override
    public FileResponse getKtpFileInfo() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile =
                userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        if (userProfile.getKtpPath() == null) {
            throw new ResourceNotFoundException("KTP", "user", user.getId());
        }

        FileStorage fileStorage =
                fileStorageRepository
                        .findByReferenceIdAndCategory(
                                String.valueOf(userProfile.getId()),
                                "KTP"
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException("KTP file", "user", user.getId()));

        return fileStorageService.getFileById(
                fileStorage.getId()
        );
    }

    @Override
    public Resource downloadSalarySlip() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile =
                userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        if (userProfile.getSalarySlipPath() == null) {
            throw new ResourceNotFoundException("Salary slip", "user", user.getId());}

        FileStorage fileStorage =
                fileStorageRepository
                        .findByReferenceIdAndCategory(String.valueOf(userProfile.getId()), "SALARY_SLIP")
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Salary slip file", "user", user.getId()));

        return fileStorageService.loadFileAsResource(
                fileStorage.getId()
        );
    }

    @Override
    public FileResponse getSalarySlipFileInfo() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User", "email", email));

        UserProfile userProfile =
                userProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("User profile", "user", user.getId()));

        if (userProfile.getSalarySlipPath() == null) {
            throw new ResourceNotFoundException("Salary slip", "user", user.getId());
        }

        FileStorage fileStorage =
                fileStorageRepository
                        .findByReferenceIdAndCategory(
                                String.valueOf(userProfile.getId()),
                                "SALARY_SLIP"
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Salary slip file", "user", user.getId()));

        return fileStorageService.getFileById(
                fileStorage.getId()
        );
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    private UserProfile findByUser(User user) {
        return userProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User profile not found"));
    }
}
