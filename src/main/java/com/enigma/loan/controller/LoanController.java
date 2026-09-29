package com.enigma.loan.controller;

import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
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
}