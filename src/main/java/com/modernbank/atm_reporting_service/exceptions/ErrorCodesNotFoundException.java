package com.modernbank.atm_reporting_service.exceptions;


import lombok.Getter;

import java.time.LocalDateTime;

public class ErrorCodesNotFoundException extends RuntimeException{

    @Getter
    private String message;

    @Getter
    private LocalDateTime timestamp;

    public ErrorCodesNotFoundException(String message, LocalDateTime timestamp){
        super(message);
        this.message = message;
        this.timestamp = timestamp;
    }
}