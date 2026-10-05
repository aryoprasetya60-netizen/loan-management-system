package com.enigma.loan.service;

import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;

import java.util.List;

public interface LoanService {
    List<LoanResponse> getAll();
    LoanResponse create(LoanRequest request);
    LoanResponse getById(Long id);
    List<LoanResponse> getMyLoans();
    LoanResponse getMyLoanById(Long id);
}
