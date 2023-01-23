package com.immovable.investmentplatform.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse{
    private int errorCode;
    private String message;
    private List<String> description;

    public ErrorResponse(int errorCode, String message, List<String> description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }

}
