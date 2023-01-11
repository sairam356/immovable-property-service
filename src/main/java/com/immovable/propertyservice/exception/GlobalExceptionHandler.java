package com.immovable.propertyservice.exception;

import com.immovable.propertyservice.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorResponse(4041, "Requested resource not found", Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(Exception ex) {
        return new ErrorResponse(4004, "Bad Request", Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler(value = {ImmovableApiException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse apiException(ImmovableApiException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponse(5001, "Internal server error...", Collections.singletonList(ex.getMessage()));
    }


}
