package dev.nokk.catalog.domain.model;

import dev.nokk.catalog.catalog.domain.exception.PriceMustBePositiveException;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CatalogTest {
    private static final String TEST_CATALOG_NAME = "товар";
    private static final BigDecimal TEST_CATALOG_PRICE = BigDecimal.TEN;

    @Test
    void changePrice_shouldUpdatePrice_whenPriceValid() {
        // given
        Catalog catalog = new Catalog(
                UUID.randomUUID(),
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                true,
                null,
                null
        );

        BigDecimal newPrice = BigDecimal.valueOf(100);

        // when
        catalog.changePrice(newPrice);

        // then
        assertThat(catalog.getPrice()).isEqualTo(newPrice);
    }

    @Test
    void changePrice_shouldThrowException_whenPriceNegative() {
        // given
        Catalog catalog = new Catalog(
                UUID.randomUUID(),
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                true,
                null,
                null
        );

        BigDecimal negative = BigDecimal.valueOf(-1);

        // when / then
        assertThatThrownBy(() -> catalog.changePrice(negative))
                .isInstanceOf(PriceMustBePositiveException.class)
                .hasMessage("Price must be more then zero");
    }

    @Test
    void changeAvailable_shouldUpdateAvailability() {
        // given
        Catalog catalog = new Catalog(
                UUID.randomUUID(),
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                true,
                null,
                null
        );

        // when
        catalog.changeAvailable(false);

        // then
        assertThat(catalog.isAvailable()).isFalse();
    }
}
