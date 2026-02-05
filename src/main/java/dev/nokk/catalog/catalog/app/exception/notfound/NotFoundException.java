package dev.nokk.catalog.catalog.app.exception.notfound;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
