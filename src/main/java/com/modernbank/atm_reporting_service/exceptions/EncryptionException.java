package com.modernbank.atm_reporting_service.exceptions;

import lombok.Getter;

public class EncryptionException extends RuntimeException{

    @Getter
    private String message;

    public EncryptionException(){
        super();
        this.message = null;
    }

    public EncryptionException(String message){
        super();
        this.message = message;
    }
}
