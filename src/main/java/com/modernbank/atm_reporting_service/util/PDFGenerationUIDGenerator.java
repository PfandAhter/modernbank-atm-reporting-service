package com.modernbank.atm_reporting_service.util;

import java.time.LocalDate;
import java.util.UUID;

public class PDFGenerationUIDGenerator {

    public static String generateRequestId(){
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        int day = localDate.getDayOfMonth();

        return "PDF"+
                year +
                UUID.randomUUID().toString().replace("-", "") +
                month +
                UUID.randomUUID().toString().replace("-","").substring(0,10) +
                day;
    }
}