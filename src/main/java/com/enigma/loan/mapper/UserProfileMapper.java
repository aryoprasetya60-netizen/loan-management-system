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

        response.setId(userProfile.getId());
        response.setUserId(userProfile.getUser().getId());
        response.setMonthlyIncome(userProfile.getMonthlyIncome());
        response.setMonthlyExpense(userProfile.getMonthlyExpense());
        response.setEmploymentStatus(userProfile.getEmploymentStatus());
        response.setKtpPath(userProfile.getKtpPath());
        response.setSalarySlipPath(userProfile.getSalarySlipPath());
        response.setCreatedAt(userProfile.getCreatedAt());
        response.setUpdatedAt(userProfile.getUpdatedAt());

        return response;
    }
}
