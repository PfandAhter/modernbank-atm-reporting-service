package com.modernbank.atm_reporting_service.websocket.service.cache;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class CacheLoadEstimatorService {

    private final KafkaTemplate<String, CreatePDFRequest> pdfGeneratorKafkaConsumer;

    public String estimateCompletionTime(){
        long queueSize = getQueueSize() + 1;
        int averageProcessingTimePerInvoice = 300; // saniye bunu degistirebiliriz...

        LocalDateTime estimatedTime = LocalDateTime.now()
                .plusSeconds((queueSize * averageProcessingTimePerInvoice) * 6);

        return estimatedTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    private long getQueueSize(){
        return pdfGeneratorKafkaConsumer.metrics().size();
    }
}