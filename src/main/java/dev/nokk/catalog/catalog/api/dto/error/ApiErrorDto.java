package dev.nokk.catalog.catalog.api.dto.error;

import java.time.OffsetDateTime;

public record ApiErrorDto(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
