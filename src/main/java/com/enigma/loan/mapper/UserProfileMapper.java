package com.enigma.loan.mapper;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.UserProfileResponse;
import com.enigma.loan.entity.UserProfile;

public class UserProfileMapper {

    public static UserProfile convertToUserProfile(
            UserProfileRequest request
    ) {
        UserProfile userProfile = new UserProfile();

        userProfile.setMonthlyIncome(request.getMonthlyIncome());
        userProfile.setMonthlyExpense(request.getMonthlyExpense());
        userProfile.setEmploymentStatus(request.getEmploymentStatus());

        return userProfile;
    }

    public static UserProfileResponse convertToUserProfileResponse(
            UserProfile userProfile
    ) {
        UserProfileResponse response = new UserProfileResponse();

        response.setId(userProfile.getUser().getId());
        response.setName(userProfile.getUser().getName());
        response.setEmail(userProfile.getUser().getEmail());
        response.setPhoneNumber(userProfile.getUser().getPhoneNumber());

        response.setMonthlyIncome(userProfile.getMonthlyIncome());
        response.setEmploymentStatus(userProfile.getEmploymentStatus());

        response.setKtpUploaded(
                userProfile.getKtpPath() != null
        );

        response.setSalarySlipUploaded(
                userProfile.getSalarySlipPath() != null
        );

        return response;
    }
}
