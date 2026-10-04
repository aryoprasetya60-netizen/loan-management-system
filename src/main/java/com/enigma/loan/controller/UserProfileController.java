package com.enigma.loan.controller;

import com.enigma.loan.dto.request.UserProfileRequest;
import com.enigma.loan.dto.response.CommonResponse;
import com.enigma.loan.dto.response.UserProfileResponse;
import com.enigma.loan.service.UserProfileService;
import com.enigma.loan.utils.constants.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
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
}
