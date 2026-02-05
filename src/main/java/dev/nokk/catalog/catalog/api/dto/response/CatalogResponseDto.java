package dev.nokk.catalog.catalog.api.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CatalogResponseDto(
        UUID id,
        String name,
        BigDecimal price,
        boolean available
) {
}
