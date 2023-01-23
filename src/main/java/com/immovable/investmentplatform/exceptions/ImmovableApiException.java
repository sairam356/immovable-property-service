package com.immovable.investmentplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImmovableApiException extends RuntimeException {
    public ImmovableApiException(String message) {
        super(message);
    }
}