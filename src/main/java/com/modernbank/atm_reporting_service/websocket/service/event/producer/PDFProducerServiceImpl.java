package com.modernbank.atm_reporting_service.websocket.service.event.producer;

import com.modernbank.atm_reporting_service.exceptions.ProcessFailedException;
import com.modernbank.atm_reporting_service.util.PDFGenerationUIDGenerator;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import com.modernbank.atm_reporting_service.websocket.controller.api.response.PDFGeneratorResponse;
import com.modernbank.atm_reporting_service.websocket.service.cache.CacheLoadEstimatorService;
import com.modernbank.atm_reporting_service.websocket.service.interfaces.IPDFProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.ERROR_OCCURRED_WHILE_GENERATING_PDF_REPORT;

@Service
@AllArgsConstructor
@Slf4j
public class PDFProducerServiceImpl implements IPDFProducerService {

    private static final String TOPIC = "pdf-generator";

    private final CacheLoadEstimatorService loadEstimatorService;

    private final KafkaTemplate<String, CreatePDFRequest> createPDFRequestKafkaTemplate;

    public PDFGeneratorResponse createATMPDFReport(CreatePDFRequest request) {
        try {
            String requestId = PDFGenerationUIDGenerator.generateRequestId();
            request.setRequestId(requestId);

            createPDFRequestKafkaTemplate.send(TOPIC,request);
            return PDFGeneratorResponse.builder()
                    .requestId(requestId)
                    .estimatedTime(loadEstimatorService.estimateCompletionTime())
                    .build();
        } catch (Exception e) {
            log.error("Error creating ATM PDF report: {}", e.getMessage());
            throw new ProcessFailedException(ERROR_OCCURRED_WHILE_GENERATING_PDF_REPORT);
        }
    }
}