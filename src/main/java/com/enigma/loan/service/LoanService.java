package com.enigma.loan.service;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;

public interface LoanService {
    LoanResponse create(LoanRequest request);
}
