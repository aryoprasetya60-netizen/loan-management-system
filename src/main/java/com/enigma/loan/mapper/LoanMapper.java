package com.enigma.loan.mapper;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.entity.Loan;

public class LoanMapper {

    public static Loan convertToLoan(LoanRequest request) {
        Loan loan = new Loan();

        loan.setRequestedAmount(request.getRequestedAmount());
        loan.setTenor(request.getTenor());
        loan.setPurpose(request.getPurpose());

        return loan;
    }

    public static LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setUserId(loan.getUser().getId());

        response.setRequestedAmount(loan.getRequestedAmount());
        response.setTenor(loan.getTenor());
        response.setPurpose(loan.getPurpose());

        // Credit Scoring
        response.setCreditScore(loan.getCreditScore());
        response.setRiskLevel(loan.getRiskLevel());
        response.setEligible(loan.getEligible());

        // AI Recommendation
        response.setAiRecommendation(loan.getAiRecommendation());
        response.setAiExplanation(loan.getAiExplanation());
        response.setAiStatus(loan.getAiStatus());
        response.setAiGeneratedAt(loan.getAiGeneratedAt());

        // Loan Status
        response.setLoanStatus(loan.getLoanStatus());

        response.setCreatedAt(loan.getCreatedAt());
        response.setUpdatedAt(loan.getUpdatedAt());

        return response;
    }
}
