package dev.nokk.catalog.app.service.impl;

import dev.nokk.catalog.catalog.app.adapter.CatalogRepository;
import dev.nokk.catalog.catalog.app.adapter.SearchEngine;
import dev.nokk.catalog.catalog.app.mapper.CommandMapper;
import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import dev.nokk.catalog.catalog.app.service.impl.CatalogServiceImpl;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceImplTest {
    private static final String TEST_CATALOG_NAME = "товар";
    private static final BigDecimal TEST_CATALOG_PRICE = BigDecimal.TEN;
    private static final boolean AVAILABLE = true;

    @Mock
    private CatalogRepository catalogRepository;

    @Mock
    private CommandMapper commandMapper;

    @Mock
    private SearchEngine searchEngine;

    @InjectMocks
    private CatalogServiceImpl service;

    @Test
    void createCatalog_shouldSaveCatalog_andIndexIt() {
        // given
        CreateCatalogCommand command =
                new CreateCatalogCommand(TEST_CATALOG_NAME, TEST_CATALOG_PRICE);

        Catalog mapped = new Catalog(
                null,
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                AVAILABLE,
                null,
                null
        );

        Catalog saved = new Catalog(
                UUID.randomUUID(),
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                true,
                null,
                null
        );

        when(commandMapper.toCatalog(command)).thenReturn(mapped);
        when(catalogRepository.save(mapped)).thenReturn(saved);

        // when
        Catalog result = service.createCatalog(command);

        // then
        assertThat(result).isEqualTo(saved);

        verify(searchEngine).saveInEngine(saved.getId(), mapped);
    }

    @Test
    void findByIdOrThrow_shouldReturnCatalog_whenExists() {
        // given
        UUID id = UUID.randomUUID();

        Catalog catalog = mock(Catalog.class);

        when(catalogRepository.findById(id)).thenReturn(Optional.of(catalog));

        // when
        Catalog result = service.findByIdOrThrow(id);

        // then
        assertThat(result).isEqualTo(catalog);
    }

    @Test
    void changePrice_shouldUpdatePrice_andSave() {
        // given
        UUID id = UUID.randomUUID();

        Catalog catalog = mock(Catalog.class);
        Catalog updated = mock(Catalog.class);

        when(catalogRepository.findById(id)).thenReturn(Optional.of(catalog));

        when(catalog.changePrice(BigDecimal.ONE)).thenReturn(updated);

        when(catalogRepository.save(updated)).thenReturn(updated);

        // when
        Catalog result = service.changePriceById(id, BigDecimal.ONE);

        // then
        assertThat(result).isEqualTo(updated);
    }
}
