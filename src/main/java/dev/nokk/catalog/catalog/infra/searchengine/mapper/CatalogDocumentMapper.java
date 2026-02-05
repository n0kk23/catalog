package dev.nokk.catalog.catalog.infra.searchengine.mapper;

import dev.nokk.catalog.catalog.domain.model.Catalog;
import dev.nokk.catalog.catalog.infra.searchengine.doc.CatalogDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogDocumentMapper {
    CatalogDocument toDocument(Catalog catalog);
}
