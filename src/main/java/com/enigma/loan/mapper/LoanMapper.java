package com.enigma.loan.mapper;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.entity.Loan;

public class LoanMapper {

    public static Loan convertToLoan(LoanRequest request) {
        Loan loan = new Loan();

        loan.setAmount(request.getAmount());
        loan.setTenor(request.getTenor());
        loan.setEmploymentStatus(request.getEmploymentStatus());

        return loan;
    }

    public static LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setUserId(loan.getUser().getId());
        response.setAmount(loan.getAmount());
        response.setTenor(loan.getTenor());
        response.setEmploymentStatus(loan.getEmploymentStatus());
        response.setCreditScore(loan.getCreditScore());
        response.setRecommendation(loan.getRecommendation());
        response.setStatus(loan.getStatus());
        response.setCreatedAt(loan.getCreatedAt());
        response.setUpdatedAt(loan.getUpdatedAt());

        return response;
    }
}
