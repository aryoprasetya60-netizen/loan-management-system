package com.enigma.loan.controller;

import com.enigma.loan.dto.response.CommonResponse;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.service.LoanService;
import com.enigma.loan.utils.constants.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/loans")
@RequiredArgsConstructor
@Tag(name="Loan Admin", description = "Loan Admin Endpoint")

public class AdminLoanController {

    private final LoanService loanService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<Object>> getAll() {

        List<LoanResponse> loans = loanService.getAll();

        CommonResponse<Object> response = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCES_GET_DATA)
                .data(loans)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<LoanResponse>> getById(
            @PathVariable Long id
    ) {
        LoanResponse response = loanService.getById(id);

        CommonResponse<LoanResponse> commonResponse =
                CommonResponse.<LoanResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(ResponseMessage.SUCCES_GET_DATA)
                        .data(response)
                        .build();

        return ResponseEntity.ok(commonResponse);
    }
}
