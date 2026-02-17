package dev.nokk.catalog.catalog.app.service.impl;

import dev.nokk.catalog.catalog.app.adapter.CatalogRepository;
import dev.nokk.catalog.catalog.app.adapter.SearchEngine;
import dev.nokk.catalog.catalog.app.exception.notfound.impl.CatalogNotFoundException;
import dev.nokk.catalog.catalog.app.mapper.CommandMapper;
import dev.nokk.catalog.catalog.app.service.CatalogService;
import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private static final int PAGE_SIZE = 20;

    private final CatalogRepository catalogRepository;
    private final CommandMapper commandMapper;
    private final SearchEngine searchEngine;

    public List<Catalog> findPage(int page) {
        return catalogRepository.findPage(
                PageRequest.of(page, PAGE_SIZE)
        );
    }

    @Override
    public List<Catalog> findAllByQueryName(String queryName) {
        List<UUID> findIds = searchEngine.findIdsByName(queryName);
        log.debug("Founded ids: {}", findIds);

        return catalogRepository.findByIds(findIds);
    }

    @Override
    public Catalog findByIdOrThrow(UUID id) {
         return findById(id);
    }

    @Override
    @Transactional
    public Catalog createCatalog(CreateCatalogCommand command) {
        log.debug("CreateCatalogCommand: {}", command);

        Catalog catalog = commandMapper.toCatalog(command);

        Catalog saved = catalogRepository.save(catalog);
        searchEngine.saveInEngine(saved.getId(), catalog);

        return saved;
    }

    @Override
    @Transactional
    public Catalog changeAvailableById(UUID id, boolean isAvailable) {
        Catalog catalog = findById(id);
        log.debug("Updating Catalog: {}. Setting available: {}", catalog, isAvailable);

        Catalog updated = catalog.changeAvailable(isAvailable);

        return catalogRepository.save(updated);
    }

    @Override
    @Transactional
    public Catalog changePriceById(UUID id, BigDecimal newPrice) {
        Catalog catalog = findById(id);
        log.debug("Updating Catalog: {}. Settings price: {}", id, newPrice);

        Catalog updated = catalog.changePrice(newPrice);

        return catalogRepository.save(updated);
    }

    private Catalog findById(UUID id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new CatalogNotFoundException(
                        "Catalog is not found by UUID: %s".formatted(id)
                ));
    }

}
