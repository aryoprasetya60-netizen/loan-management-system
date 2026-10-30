package com.enigma.loan.service.impl;

import com.enigma.loan.dto.request.CreditScoreRequest;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.CreditScoreResponse;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.entity.Loan;
import com.enigma.loan.entity.User;
import com.enigma.loan.entity.UserProfile;
import com.enigma.loan.mapper.LoanMapper;
import com.enigma.loan.repository.LoanRepository;
import com.enigma.loan.repository.UserProfileRepository;
import com.enigma.loan.repository.UserRepository;
import com.enigma.loan.service.LoanService;
import com.enigma.loan.utils.enums.LoanStatus;
import com.enigma.loan.utils.exception.BadRequestException;
import com.enigma.loan.utils.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final RestClient restClient;

    @Override
    public List<LoanResponse> getAll() {
        List<Loan> loans = loanRepository.findAll();

        return loans.stream()
                .map(LoanMapper::convertToLoanResponse)
                .toList();
    }

    @Override
    @Transactional
    public LoanResponse create(LoanRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = findByUsername(username);

        UserProfile userProfile = userProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User profile not found"));

        if (userProfile.getMonthlyIncome() == null) {
            throw new BadRequestException("Monthly income belum diisi");
        }

        if (userProfile.getMonthlyExpense() == null) {
            throw new BadRequestException("Monthly expense belum diisi");
        }

        if (userProfile.getEmploymentStatus() == null) {
            throw new BadRequestException("Employment status belum diisi");
        }
        if (userProfile.getKtpPath() == null) {
            throw new BadRequestException("KTP belum diupload");
        }

        if (userProfile.getSalarySlipPath() == null) {
            throw new BadRequestException("Salary slip belum diupload");
        }

        Loan loan = LoanMapper.convertToLoan(request);

        loan.setUser(user);

        loan.setLoanStatus(LoanStatus.PENDING);

        String customerId = String.format("CUS-%03d", user.getId());

        CreditScoreRequest creditScoreRequest =
                CreditScoreRequest.builder()
                        .customerId(customerId)
                        .monthlyIncome(userProfile.getMonthlyIncome())
                        .monthlyExpense(userProfile.getMonthlyExpense())
                        .requestedAmount(request.getRequestedAmount())
                        .tenor(request.getTenor())
                        .build();

        // 7. Panggil Credit Scoring Service
        CreditScoreResponse creditScoreResponse =
                restClient.post()
                        .uri("/api/v1/credit-scores")
                        .body(creditScoreRequest)
                        .retrieve()
                        .body(CreditScoreResponse.class);

        // 8. Simpan hasil Credit Scoring sebagai snapshot Loan
        loan.setCreditScore(creditScoreResponse.getScore());
        loan.setRiskLevel(creditScoreResponse.getRiskLevel());
        loan.setEligible(creditScoreResponse.getEligible());

        // 9. Simpan Loan
        Loan savedLoan = loanRepository.save(loan);

        return LoanMapper.convertToLoanResponse(savedLoan);
    }

    @Override
    public LoanResponse getById(Long id) {
        Loan loan = findById(id);
        return LoanMapper.convertToLoanResponse(loan);
    }

    @Override
    public List<LoanResponse> getMyLoans() {
        // 1. Ambil username user yang sedang login dari JWT Security Context
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // 2. Cari user berdasarkan username
        User user = findByUsername(username);

        // 3. Ambil semua loan milik user tersebut
        List<Loan> loans = loanRepository.findByUser(user);

        // 4. Ubah entity Loan menjadi DTO LoanResponse
        return loans.stream()
                .map(LoanMapper::convertToLoanResponse)
                .toList();
    }

    @Override
    public LoanResponse getMyLoanById(Long id) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = findByUsername(username);

        Loan loan = findById(id);

        if (!loan.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Anda tidak memiliki akses ke loan ini");
        }

        return LoanMapper.convertToLoanResponse(loan);
    }

    private Loan findById(Long id){
        return loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));
    }

    private User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}