package dev.nokk.catalog.catalog.infra.port;

import dev.nokk.catalog.catalog.app.adapter.SearchEngine;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import dev.nokk.catalog.catalog.infra.searchengine.doc.CatalogDocument;
import dev.nokk.catalog.catalog.infra.searchengine.mapper.CatalogDocumentMapper;
import dev.nokk.catalog.catalog.infra.searchengine.repository.CatalogSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CatalogSearchRepositoryAdapter implements SearchEngine {
    private final CatalogSearchRepository catalogSearchRepository;
    private final CatalogDocumentMapper catalogMapper;

    @Override
    public void saveInEngine(UUID id, Catalog catalog) {
        CatalogDocument doc = catalogMapper.toDocument(catalog);
        doc.setId(id);

        catalogSearchRepository.save(doc);
    }

    @Override
    public List<UUID> findIdsByName(String name) {
        return catalogSearchRepository.findByName(name).stream()
                .map(CatalogDocument::getId)
                .toList();
    }
}
