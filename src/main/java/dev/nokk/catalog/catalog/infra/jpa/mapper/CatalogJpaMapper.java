package dev.nokk.catalog.catalog.infra.jpa.mapper;

import dev.nokk.catalog.catalog.domain.model.Catalog;
import dev.nokk.catalog.catalog.infra.jpa.entity.CatalogJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogJpaMapper {
    Catalog toDomain(CatalogJpaEntity entity);
    CatalogJpaEntity toEntity(Catalog domain);
}
