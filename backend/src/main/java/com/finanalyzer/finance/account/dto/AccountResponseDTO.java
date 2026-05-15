package com.finanalyzer.finance.account.dto;

import com.finanalyzer.finance.account.AccountType;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponseDTO(
        Long id,
        String name,
        String institutionName,
        AccountType type,
        BigDecimal balance,
        String currency,
        BigDecimal creditLimit,
        BigDecimal availableCredit,
        Instant createdAt
) {
}