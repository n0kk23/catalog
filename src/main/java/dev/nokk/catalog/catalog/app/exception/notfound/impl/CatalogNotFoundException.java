package dev.nokk.catalog.catalog.app.exception.notfound.impl;

import dev.nokk.catalog.catalog.app.exception.notfound.NotFoundException;

public class CatalogNotFoundException extends NotFoundException {
    public CatalogNotFoundException(String message) {
        super(message);
    }
}
