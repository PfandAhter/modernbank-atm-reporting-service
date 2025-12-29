package com.modernbank.atm_reporting_service.exceptions;


import lombok.Getter;

public class CreateFailedException extends RuntimeException{

    @Getter
    private String message;

    @Getter
    private String dynamicValue;

    public CreateFailedException(){
        super();
        this.message = null;
        this.dynamicValue = null;
    }

    public CreateFailedException(String message){
        super();
        this.message = message;
        this.dynamicValue = null;
    }

    public CreateFailedException(String message, String dynamicValue){
        super();
        this.message = message;
        this.dynamicValue = dynamicValue;
    }
}