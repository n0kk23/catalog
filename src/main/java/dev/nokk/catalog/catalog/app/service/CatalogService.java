package dev.nokk.catalog.catalog.app.service;

import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import dev.nokk.catalog.catalog.domain.model.Catalog;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CatalogService {
    List<Catalog> findPage(int page);
    List<Catalog> findAllByQueryName(String queryName);
    Catalog findByIdOrThrow(UUID id);
    Catalog createCatalog(CreateCatalogCommand command);
    Catalog changeAvailableById(UUID id, boolean isAvailable);
    Catalog changePriceById(UUID id, BigDecimal newPrice);
}
