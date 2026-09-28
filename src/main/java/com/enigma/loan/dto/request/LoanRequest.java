package com.enigma.loan.dto.request;

import com.enigma.loan.utils.enums.EmploymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequest {

    @NotNull(message = "Loan amount wajib diisi")
    @DecimalMin(value = "100000", message = "Loan amount minimal 100000")
    private BigDecimal loanAmount;

    @NotNull(message = "Monthly income wajib diisi")
    @DecimalMin(value = "0", message = "Monthly income tidak boleh negatif")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Monthly expense wajib diisi")
    @DecimalMin(value = "0", message = "Monthly expense tidak boleh negatif")
    private BigDecimal monthlyExpense;

    @NotNull(message = "Tenor wajib diisi")
    @Positive(message = "Tenor harus lebih dari 0")
    private Integer tenor;

    @NotBlank(message = "Purpose wajib diisi")
    private String purpose;

    @NotNull(message = "Employment status wajib diisi")
    private EmploymentStatus employmentStatus;
}
