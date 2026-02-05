package dev.nokk.catalog.catalog.api.mapper;

import dev.nokk.catalog.catalog.api.dto.response.CatalogResponseDto;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogApiMapper {
    CatalogResponseDto toResponse(Catalog catalog);
}
