package dev.nokk.catalog.catalog.api.mapper;

import dev.nokk.catalog.catalog.api.dto.request.CreateCatalogRequestDto;
import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestApiMapper {
    CreateCatalogCommand toCommand(CreateCatalogRequestDto request);
}
