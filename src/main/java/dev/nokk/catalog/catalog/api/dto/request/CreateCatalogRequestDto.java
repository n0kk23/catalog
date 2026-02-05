package dev.nokk.catalog.catalog.api.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateCatalogRequestDto(
        @NotBlank
        @Size(min = 1, max = 255)
        String name,

        @NotNull
        @Positive
        @Digits(integer = 17, fraction = 2)
        BigDecimal price
) {
}
