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
    private String name;
    private String email;
    private String phoneNumber;
    private BigDecimal monthlyIncome;
    private EmploymentStatus employmentStatus;
    private boolean ktpUploaded;
    private boolean salarySlipUploaded;
}