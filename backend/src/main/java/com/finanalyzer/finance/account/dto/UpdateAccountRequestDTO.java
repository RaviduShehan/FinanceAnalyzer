package com.finanalyzer.finance.account.dto;

import com.finanalyzer.finance.account.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateAccountRequestDTO(

        @NotBlank(message = "Account name is required")
        @Size(max = 100, message = "Account name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Institution name is required")
        @Size(max = 100, message = "Institution name must not exceed 100 characters")
        String institutionName,

        @NotNull(message = "Account type is required")
        AccountType type,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.00", message = "Balance cannot be negative")
        BigDecimal balance,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
        String currency,

        @DecimalMin(value = "0.00", message = "Credit limit cannot be negative")
        BigDecimal creditLimit
) {
}