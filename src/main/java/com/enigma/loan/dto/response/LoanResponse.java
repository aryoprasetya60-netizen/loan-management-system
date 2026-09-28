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

    private BigDecimal amount;

    private Integer tenor;

    private EmploymentStatus employmentStatus;

    private Integer creditScore;

    private String recommendation;

    private LoanStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
