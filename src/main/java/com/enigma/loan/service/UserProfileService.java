package com.enigma.loan.service;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse create(UserProfileRequest request);

    UserProfileResponse get();

    UserProfileResponse update(UserProfileRequest request);
}
