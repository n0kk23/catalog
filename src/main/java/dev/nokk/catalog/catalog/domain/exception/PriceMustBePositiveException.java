package dev.nokk.catalog.catalog.domain.exception;

public class PriceMustBePositiveException extends RuntimeException {
    public PriceMustBePositiveException(String message) {
        super(message);
    }
}
