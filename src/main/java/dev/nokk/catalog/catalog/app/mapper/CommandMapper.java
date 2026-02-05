package dev.nokk.catalog.catalog.app.mapper;

import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {
    private final static boolean AVAILABLE = true;

    public Catalog toCatalog(CreateCatalogCommand command) {
        return new Catalog(
                null,
                command.name(),
                command.price(),
                AVAILABLE,
                null,
                null
        );
    }
}
