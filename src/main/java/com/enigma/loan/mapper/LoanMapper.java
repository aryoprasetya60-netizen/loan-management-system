package com.enigma.loan.mapper;
import com.enigma.loan.dto.request.LoanRequest;
import com.enigma.loan.dto.response.LoanResponse;
import com.enigma.loan.entity.Loan;

public class LoanMapper {

    public static Loan convertToLoan(LoanRequest request) {
        Loan loan = new Loan();

        loan.setLoanAmount(request.getLoanAmount());
        loan.setMonthlyIncome(request.getMonthlyIncome());
        loan.setMonthlyExpense(request.getMonthlyExpense());
        loan.setTenor(request.getTenor());
        loan.setPurpose(request.getPurpose());
        loan.setEmploymentStatus(request.getEmploymentStatus());

        return loan;
    }

    public static LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setUserId(loan.getUser().getId());
        response.setLoanAmount(loan.getLoanAmount());
        response.setTenor(loan.getTenor());
        response.setPurpose(loan.getPurpose());
        response.setMonthlyIncome(loan.getMonthlyIncome());
        response.setMonthlyExpense(loan.getMonthlyExpense());
        response.setEmploymentStatus(loan.getEmploymentStatus());
        response.setStatus(loan.getStatus());
        response.setCreditScore(loan.getCreditScore());
        response.setCreditRecommendation(loan.getRecommendation());
        response.setCreatedAt(loan.getCreatedAt());
        response.setUpdatedAt(loan.getUpdatedAt());

        return response;
    }
}
