package dev.nokk.catalog.catalog.app.adapter;

import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogRepository {
    List<Catalog> findPage(Pageable pageable);
    List<Catalog> findByIds(List<UUID> ids);
    Optional<Catalog> findById(UUID id);
    Catalog save(Catalog catalog);
}
