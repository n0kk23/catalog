package dev.nokk.catalog.catalog.app.adapter;

import dev.nokk.catalog.catalog.domain.model.Catalog;

import java.util.List;
import java.util.UUID;

public interface SearchEngine {
    void saveInEngine(UUID id, Catalog catalog);
    List<UUID> findIdsByName(String name);
}
