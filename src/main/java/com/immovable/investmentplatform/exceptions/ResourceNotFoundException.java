package com.immovable.investmentplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.immovable.investmentplatform.enums.ResourceType;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(ResourceType resourceType) {
        super(String.format("Requested resource not found with type: %s", resourceType));
    }

    public ResourceNotFoundException(ResourceType resourceType, String... givenParams) {
        super(String.format("Requested resource not found with type: %s for given params [%s]", resourceType, String.join(",", givenParams)));
    }
}
