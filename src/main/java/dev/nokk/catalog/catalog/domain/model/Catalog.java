package dev.nokk.catalog.catalog.domain.model;

import dev.nokk.catalog.catalog.domain.exception.PriceMustBePositiveException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Catalog {
    private final UUID id;
    private String name;
    private BigDecimal price;
    private boolean available;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Catalog changeAvailable(boolean isAvailable) {
        this.available = isAvailable;

        return this;
    }

    public Catalog changePrice(BigDecimal price) {
        Objects.requireNonNull(price, "price must be not null");

        if (price.longValue() < 0) {
            throw new PriceMustBePositiveException(
                    "Price must be more then zero"
            );
        }

        this.price = price;

        return this;
    }
}
