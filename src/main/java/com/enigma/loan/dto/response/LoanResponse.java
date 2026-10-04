package com.enigma.loan.dto.response;

import com.enigma.loan.utils.enums.AiRecommendation;
import com.enigma.loan.utils.enums.AiStatus;
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

    private BigDecimal requestedAmount;

    private Integer tenor;

    private String purpose;

    private Integer creditScore;

    private String riskLevel;

    private Boolean eligible;

    private AiRecommendation aiRecommendation;

    private String aiExplanation;

    private AiStatus aiStatus;

    private LocalDateTime aiGeneratedAt;

    private LoanStatus loanStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
