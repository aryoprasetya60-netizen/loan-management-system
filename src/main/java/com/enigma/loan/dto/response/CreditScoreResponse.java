package com.enigma.loan.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreResponse {

    private String customerId;
    private Integer score;
    private String riskLevel;
    private Boolean eligible;
}