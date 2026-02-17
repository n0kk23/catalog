package dev.nokk.catalog.catalog.api.controller;

import dev.nokk.catalog.catalog.api.dto.request.ChangeCatalogAvailableRequestDto;
import dev.nokk.catalog.catalog.api.dto.request.ChangePriceRequestDto;
import dev.nokk.catalog.catalog.api.dto.request.CreateCatalogRequestDto;
import dev.nokk.catalog.catalog.api.dto.response.CatalogResponseDto;
import dev.nokk.catalog.catalog.api.mapper.CatalogApiMapper;
import dev.nokk.catalog.catalog.api.mapper.RequestApiMapper;
import dev.nokk.catalog.catalog.app.service.CatalogService;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;
    private final CatalogApiMapper catalogMapper;
    private final RequestApiMapper requestMapper;

    @GetMapping("/page={page}")
    public List<CatalogResponseDto> findPage(
            @PathVariable int page
    ) {
        return catalogService.findPage(page).stream()
                .map(catalogMapper::toResponse)
                .toList();
    }

    @GetMapping("/id={id}")
    public CatalogResponseDto findById(
            @PathVariable UUID id
    ) {
        return catalogMapper.toResponse(
                catalogService.findByIdOrThrow(id)
        );
    }

    @GetMapping("/search")
    public List<CatalogResponseDto> findByQueryName(
            @RequestParam("query") String queryName
    ) {
        return catalogService.findAllByQueryName(queryName).stream()
                .map(catalogMapper::toResponse)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatalogResponseDto createCatalog(
            @RequestBody @Valid CreateCatalogRequestDto request
    ) {
        log.debug("CreateCatalogRequestDto: {}", request);

        Catalog created = catalogService.createCatalog(
                requestMapper.toCommand(request)
        );

        return catalogMapper.toResponse(created);
    }

    @PatchMapping("/id={id}/change-available")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CatalogResponseDto changeAvailable(
            @PathVariable UUID id,
            @RequestBody @Valid ChangeCatalogAvailableRequestDto request
    ) {
        boolean isAvailable = request.isAvailable();
        Catalog updated = catalogService.changeAvailableById(id, isAvailable);

        return catalogMapper.toResponse(updated);
    }

    @PatchMapping("/id={id}/change-price")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CatalogResponseDto changePrice(
            @PathVariable UUID id,
            @RequestBody @Valid ChangePriceRequestDto request
    ) {
        BigDecimal newPrice = request.price();
        Catalog updated = catalogService.changePriceById(id, newPrice);

        return catalogMapper.toResponse(updated);
    }

}
