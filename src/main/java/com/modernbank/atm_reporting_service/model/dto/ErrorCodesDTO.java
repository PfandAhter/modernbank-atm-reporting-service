package com.modernbank.atm_reporting_service.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorCodesDTO {

    private String id;

    private String error;

    private String description;
}