package com.enigma.loan.dto.response;

import com.enigma.loan.utils.enums.EmploymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long id;
    private Long userId;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private EmploymentStatus employmentStatus;
    private String ktpPath;
    private String salarySlipPath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}