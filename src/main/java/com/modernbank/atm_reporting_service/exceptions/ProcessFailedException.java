package com.modernbank.atm_reporting_service.exceptions;


import lombok.Getter;

public class ProcessFailedException extends RuntimeException{

    @Getter
    private String message;

    @Getter
    private String dynamicValue;

    public ProcessFailedException(){
        super();
        this.message = null;
        this.dynamicValue = null;
    }

    public ProcessFailedException(String message){
        super();
        this.message = message;
        this.dynamicValue = null;
    }

    public ProcessFailedException(String message, String dynamicValue){
        super();
        this.message = message;
        this.dynamicValue = dynamicValue;
    }
}