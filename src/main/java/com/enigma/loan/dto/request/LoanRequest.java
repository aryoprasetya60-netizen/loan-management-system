package com.enigma.loan.dto.request;

import com.enigma.loan.utils.enums.EmploymentStatus;
import jakarta.validation.constraints.DecimalMin;
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

    @NotNull(message = "Amount wajib diisi")
    @DecimalMin(value = "100000", message = "Amount minimal 100000")
    private BigDecimal amount;

    @NotNull(message = "Tenor wajib diisi")
    @Positive(message = "Tenor harus lebih dari 0")
    private Integer tenor;

    @NotNull(message = "Employment status wajib diisi")
    private EmploymentStatus employmentStatus;
}
