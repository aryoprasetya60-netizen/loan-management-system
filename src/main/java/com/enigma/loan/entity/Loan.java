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
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private Integer tenor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentStatus employmentStatus;

    private Integer creditScore;

    @Column(nullable = false)
    private BigDecimal monthlyIncome;

    @Column(nullable = false)
    private BigDecimal monthlyExpense;

    private String creditRecommendation;

    @Column(nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    public Loan(User user, BigDecimal loanAmount, Integer tenor, EmploymentStatus employmentStatus, Integer creditScore, BigDecimal monthlyIncome, BigDecimal monthlyExpense, String recommendation, String purpose, LoanStatus status) {
        this.user = user;
        this.loanAmount = loanAmount;
        this.tenor = tenor;
        this.employmentStatus = employmentStatus;
        this.creditScore = creditScore;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpense = monthlyExpense;
        this.creditRecommendation = recommendation;
        this.purpose = purpose;
        this.status = status;
    }
}
