package com.modernbank.atm_reporting_service.websocket.service.event.producer;

import com.modernbank.atm_reporting_service.websocket.controller.api.response.BaseResponse;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IInvoicePDFProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class InvoicePDFProducerImpl implements IInvoicePDFProducer {


    public BaseResponse createATMPDFReport(String atmId) {
        try {
            log.info("Creating ATM PDF report for ATM ID: {}", atmId);
            // Logic to create the ATM PDF report
            return new BaseResponse("ATM PDF report created successfully");
        } catch (Exception e) {
            log.error("Error creating ATM PDF report: {}", e.getMessage());
            return new BaseResponse("Error creating ATM PDF report");
        }
    }
}