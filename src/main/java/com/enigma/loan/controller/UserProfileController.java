package com.enigma.loan.controller;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.CommonResponse;
import com.enigma.loan.dto.response.FileResponse;
import com.enigma.loan.dto.response.UserProfileResponse;
import com.enigma.loan.service.UserProfileService;
import com.enigma.loan.utils.ResponseUtil;
import com.enigma.loan.utils.constants.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name="User Profile", description = "User Profile endpoint")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<CommonResponse<UserProfileResponse>> create(
            @Valid @RequestBody UserProfileRequest request
    ) {

        UserProfileResponse response =
                userProfileService.create(request);

        CommonResponse<UserProfileResponse> commonResponse =
                CommonResponse.<UserProfileResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message(ResponseMessage.SUCCES_CREATE_DATA)
                        .data(response)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<UserProfileResponse>> get() {

        UserProfileResponse response =
                userProfileService.get();

        CommonResponse<UserProfileResponse> commonResponse =
                CommonResponse.<UserProfileResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCES_GET_DATA)
                        .data(response)
                        .build();

        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<UserProfileResponse>> update(
            @Valid @RequestBody UserProfileRequest request
    ) {

        UserProfileResponse response =
                userProfileService.update(request);

        CommonResponse<UserProfileResponse> commonResponse =
                CommonResponse.<UserProfileResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCES_UPDATE_DATA)
                        .data(response)
                        .build();

        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping(
            value = "/documents/ktp",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CommonResponse<UserProfileResponse>> uploadKtp(
            @RequestParam("file") MultipartFile file
    ) {

        UserProfileResponse response =
                userProfileService.uploadKtp(file);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_SAVE_DATA,
                response
        );
    }

    @PostMapping(
            value = "/documents/salary-slip",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CommonResponse<UserProfileResponse>> uploadSalarySlip(@RequestParam("file") MultipartFile file) {
        UserProfileResponse response =
                userProfileService.uploadSalarySlip(file);

        return ResponseUtil.buildResponse(
                HttpStatus.OK,
                ResponseMessage.SUCCES_SAVE_DATA,
                response
        );
    }

    @GetMapping("/documents/ktp")
    public ResponseEntity<Resource> downloadKtp() {

        Resource resource =
                userProfileService.downloadKtp();

        FileResponse fileInfo =
                userProfileService.getKtpFileInfo();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(fileInfo.getFileType())
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileInfo.getName() + "\""
                )
                .body(resource);
    }

    @GetMapping("/documents/salary-slip")
    public ResponseEntity<Resource> downloadSalarySlip() {

        Resource resource =
                userProfileService.downloadSalarySlip();

        FileResponse fileInfo =
                userProfileService.getSalarySlipFileInfo();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                fileInfo.getFileType()
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileInfo.getName() +
                                "\""
                )
                .body(resource);
    }
}
