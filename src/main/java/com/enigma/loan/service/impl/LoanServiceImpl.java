package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.CreditScoreRequest;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.CreditScoreResponse;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.entity.Loan;
import com.enigma.loan.entity.User;
import com.enigma.loan.mapper.LoanMapper;
import com.enigma.loan.repository.LoanRepository;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.service.LoanService;
import com.enigma.loan.utils.enums.LoanStatus;
import com.enigma.loan.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final RestClient restClient;

    @Override
    public LoanResponse create(LoanRequest request) {

        // 1. Ambil user yang sedang login
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = findByEmail(email);

        // 2. Convert request menjadi Loan entity
        Loan loan = LoanMapper.convertToLoan(request);

        // 3. Hubungkan Loan dengan User
        loan.setUser(user);

        // 4. Status awal Loan
        loan.setStatus(LoanStatus.PENDING);

        // 5. Buat customerId untuk Credit Scoring Service
        String customerId = String.format(
                "CUS-%03d",
                user.getId()
        );

        // 6. Buat request ke Credit Scoring Service
        CreditScoreRequest creditScoreRequest =
                CreditScoreRequest.builder()
                        .customerId(customerId)
                        .monthlyIncome(request.getMonthlyIncome())
                        .monthlyExpense(request.getMonthlyExpense())
                        .requestedAmount(request.getLoanAmount())
                        .tenor(request.getTenor())
                        .build();

        // 7. Panggil Credit Scoring Service
        CreditScoreResponse creditScoreResponse =
                restClient.post()
                        .uri("/api/v1/credit-scores")
                        .body(creditScoreRequest)
                        .retrieve()
                        .body(CreditScoreResponse.class);

        // 8. Simpan hasil Credit Scoring ke Loan
        loan.setCreditScore(creditScoreResponse.getScore());

        loan.setCreditRecommendation(
                creditScoreResponse.getRiskLevel()
                        + " - "
                        + (creditScoreResponse.getEligible()
                        ? "ELIGIBLE"
                        : "NOT ELIGIBLE")
        );

        // 9. Simpan Loan ke database
        Loan savedLoan = loanRepository.save(loan);

        // 10. Convert entity menjadi response
        return LoanMapper.convertToLoanResponse(savedLoan);
    }

    @Override
    public LoanResponse getById(Long id) {
        Loan loan = findById(id);
        return LoanMapper.convertToLoanResponse(loan);
    }

    @Override
    public List<LoanResponse> getMyLoans() {
        // 1. Ambil email user yang sedang login dari JWT Security Context
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Cari user berdasarkan email
        User user = findByEmail(email);

        // 3. Ambil semua loan milik user tersebut
        List<Loan> loans = loanRepository.findByUser(user);

        // 4. Ubah entity Loan menjadi DTO LoanResponse
        return loans.stream()
                .map(LoanMapper::convertToLoanResponse)
                .toList();
    }

    private Loan findById(Long id){
        return loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));
    }

    private User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}