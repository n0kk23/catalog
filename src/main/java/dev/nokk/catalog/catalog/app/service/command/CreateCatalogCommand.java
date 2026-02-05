package dev.nokk.catalog.catalog.app.service.command;

import java.math.BigDecimal;

public record CreateCatalogCommand(
        String name,
        BigDecimal price
) {
}
