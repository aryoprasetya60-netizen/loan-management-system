package com.enigma.loan.dto.request;

import com.enigma.loan.utils.enums.EmploymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {

    @NotNull(message = "Monthly income wajib diisi")
    @DecimalMin(value = "0", message = "Monthly income tidak boleh negatif")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Monthly expense wajib diisi")
    @DecimalMin(value = "0", message = "Monthly expense tidak boleh negatif")
    private BigDecimal monthlyExpense;

    @NotNull(message = "Employment status wajib diisi")
    private EmploymentStatus employmentStatus;
}