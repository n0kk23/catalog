package dev.nokk.catalog.catalog.infra.searchengine.repository;

import dev.nokk.catalog.catalog.infra.searchengine.doc.CatalogDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface CatalogSearchRepository extends ElasticsearchRepository<CatalogDocument, UUID> {
    List<CatalogDocument> findByName(String name);
}
