package dev.nokk.catalog.catalog.api.handler;

import dev.nokk.catalog.catalog.api.dto.error.ApiErrorDto;
import dev.nokk.catalog.catalog.app.exception.notfound.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Objects;

@RestControllerAdvice
public class CatalogHandler {
    private final static String NOT_FOUND = "Not found";
    private final static String BAD_REQUEST = "Bad request";

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleNotFoundException(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return new ApiErrorDto(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = Objects.requireNonNull(
                    ex
                            .getBindingResult()
                            .getFieldError()
                )
                .getDefaultMessage();

        return new ApiErrorDto(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return new ApiErrorDto(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

}
