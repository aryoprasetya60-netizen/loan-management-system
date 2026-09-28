package com.enigma.loan.dto.request;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreRequest {

    private String customerId;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal requestedLoanAmount;
    private Integer tenor;
}