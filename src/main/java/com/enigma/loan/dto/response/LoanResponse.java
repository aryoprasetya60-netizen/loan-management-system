package com.enigma.loan.dto.response;

import com.enigma.loan.utils.enums.EmploymentStatus;
import com.enigma.loan.utils.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponse {

    private Long id;
    private Long userId;
    private BigDecimal loanAmount;
    private Integer tenor;
    private String purpose;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private EmploymentStatus employmentStatus;
    private LoanStatus status;
    private Integer creditScore;
    private String creditRecommendation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
