package com.enigma.loan.service.impl;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    @Override
    public LoanResponse create(LoanRequest request) {
        return null;
    }
}
