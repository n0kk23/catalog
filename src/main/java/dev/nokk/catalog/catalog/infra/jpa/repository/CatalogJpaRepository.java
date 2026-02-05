package dev.nokk.catalog.catalog.infra.jpa.repository;

import dev.nokk.catalog.catalog.infra.jpa.entity.CatalogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatalogJpaRepository extends JpaRepository<CatalogJpaEntity, UUID> {
}
