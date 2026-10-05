package com.enigma.loan.service;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.dto.response.UserProfileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UserProfileService {

    UserProfileResponse create(UserProfileRequest request);

    UserProfileResponse get();

    UserProfileResponse update(UserProfileRequest request);

    UserProfileResponse uploadKtp(MultipartFile file);

    UserProfileResponse uploadSalarySlip(MultipartFile file);

    Resource downloadKtp();

    FileResponse getKtpFileInfo();

    Resource downloadSalarySlip();

    FileResponse getSalarySlipFileInfo();
}
