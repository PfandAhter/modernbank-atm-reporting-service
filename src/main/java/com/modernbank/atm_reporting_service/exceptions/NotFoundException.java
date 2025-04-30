package com.modernbank.atm_reporting_service.exceptions;

import lombok.Getter;

public class NotFoundException extends RuntimeException{

    @Getter
    private String message;

    @Getter
    private String dynamicValue;

    public NotFoundException(){
        super();
        this.message = null;
    }

    public NotFoundException(String message){
        super();
        this.message = message;
    }

    public NotFoundException(String message, String dynamicValue){
        super();
        this.message = message;
        this.dynamicValue = dynamicValue;
    }
}