package com.enigma.loan.entity;
import com.enigma.loan.utils.enums.AiRecommendation;
import com.enigma.loan.utils.enums.AiStatus;
import com.enigma.loan.utils.enums.EmploymentStatus;
import com.enigma.loan.utils.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private Integer tenor;

    @Column(nullable = false)
    private String purpose;

    // Credit Scoring Snapshot
    private Integer creditScore;

    private String riskLevel;

    private Boolean eligible;

    // AI Recommendation
    @Enumerated(EnumType.STRING)
    private AiRecommendation aiRecommendation;

    @Column(columnDefinition = "TEXT")
    private String aiExplanation;

    @Enumerated(EnumType.STRING)
    private AiStatus aiStatus;

    private LocalDateTime aiGeneratedAt;

    // Loan Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus loanStatus;
}
