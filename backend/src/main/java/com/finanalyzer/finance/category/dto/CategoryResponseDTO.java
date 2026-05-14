package com.finanalyzer.finance.category.dto;

import com.finanalyzer.finance.category.CategoryType;

import java.time.Instant;

public record CategoryResponseDTO(
        Long id,
        String name,
        CategoryType type,
        Instant createdAt
) {
}