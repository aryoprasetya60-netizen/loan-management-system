package com.enigma.loan.entity;
import com.enigma.loan.utils.enums.EmploymentStatus;
import com.enigma.loan.utils.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer tenor;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false)
    private EmploymentStatus employmentStatus;

    @Column(name = "credit_score")
    private Integer creditScore;

    private String recommendation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    public Loan(User user, BigDecimal amount, Integer tenor, EmploymentStatus employmentStatus, Integer creditScore, String recommendation, LoanStatus status) {
        this.user = user;
        this.amount = amount;
        this.tenor = tenor;
        this.employmentStatus = employmentStatus;
        this.creditScore = creditScore;
        this.recommendation = recommendation;
        this.status = status;
    }
}
