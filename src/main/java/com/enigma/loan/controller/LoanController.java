package com.enigma.loan.controller;

import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.CommonResponse;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.service.LoanService;
import com.enigma.loan.utils.constants.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> create(
            @Valid @RequestBody LoanRequest request
    ) {
        LoanResponse response = loanService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<LoanResponse>> getById(
            @PathVariable Long id
    ) {
        LoanResponse response = loanService.getById(id);

        CommonResponse<LoanResponse> commonResponse = CommonResponse.<LoanResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCES_GET_DATA)
                .data(response)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CommonResponse<Object>> getMyLoans() {
        List<LoanResponse> loans = loanService.getMyLoans();

        CommonResponse<Object> response = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCES_GET_DATA)
                .data(loans)
                .build();

        return ResponseEntity.ok(response);
    }
}