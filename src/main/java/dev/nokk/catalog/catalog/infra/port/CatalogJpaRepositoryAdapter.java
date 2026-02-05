package dev.nokk.catalog.catalog.infra.port;

import dev.nokk.catalog.catalog.app.adapter.CatalogRepository;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import dev.nokk.catalog.catalog.infra.jpa.entity.CatalogJpaEntity;
import dev.nokk.catalog.catalog.infra.jpa.mapper.CatalogJpaMapper;
import dev.nokk.catalog.catalog.infra.jpa.repository.CatalogJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CatalogJpaRepositoryAdapter implements CatalogRepository {
    private final CatalogJpaRepository catalogRepository;
    private final CatalogJpaMapper catalogMapper;

    @Override
    public List<Catalog> findPage(Pageable pageable) {
        return catalogRepository.findAll(pageable).getContent().stream()
                .map(catalogMapper::toDomain)
                .toList();
    }

    @Override
    public List<Catalog> findByIds(List<UUID> ids) {
        return catalogRepository.findAllById(ids).stream()
                .map(catalogMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Catalog> findById(UUID id) {
        return catalogRepository.findById(id)
                .map(catalogMapper::toDomain);
    }

    @Override
    public Catalog save(Catalog catalog) {
        CatalogJpaEntity entity = catalogMapper.toEntity(catalog);

        CatalogJpaEntity saved = catalogRepository.save(entity);
        log.debug("CatalogJpaEntity saved in db: {}", saved);
        return catalogMapper.toDomain(saved);
    }
}
