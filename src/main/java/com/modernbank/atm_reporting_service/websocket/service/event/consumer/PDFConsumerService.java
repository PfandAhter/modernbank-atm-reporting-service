package com.modernbank.atm_reporting_service.websocket.service.event.consumer;

import com.modernbank.atm_reporting_service.model.record.PDFContentData;
import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import com.modernbank.atm_reporting_service.websocket.service.cache.ATMReportCacheService;
import com.modernbank.atm_reporting_service.websocket.service.encryption.PDFEncryptionServiceImpl;
import com.modernbank.atm_reporting_service.websocket.service.invoicegenerator.PDFGenerationFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PDFConsumerService {

    private static final String TOPIC = "pdf-generator";

    private final PDFGenerationFactory generationFactory;

    private final PDFEncryptionServiceImpl encryptionService;

    private final ATMReportCacheService cacheService;

    @KafkaListener(topics = TOPIC,
            groupId = "pdf-consumer-group",
            containerFactory = "pdfKafkaListenerContainerFactory")
    public void consumePDF(CreatePDFRequest request){
        try{
            byte[] pdf = generationFactory.generate(request.getGenerationType(),request.getDetails());
            PDFEncryptionServiceImpl.EncryptedData data = encryptionService.encryptPDF(pdf, request.getRequestId());
            PDFContentData contentData = new PDFContentData(data.data(), data.salt());
            cacheService.cachePDF(request.getRequestId(), contentData);

            log.info("PDF is generated and encrypted successfully requestId: {}", request.getRequestId());
        }catch (Exception e){
            log.error("Error in consuming PDF: {}", e.getMessage());
        }
    }

    public Optional<byte[]> getInvoicePdf(String requestId, String password) {
        Optional<PDFContentData> data = cacheService.getPDF(requestId);
        if(data.isPresent()){
            byte[] pdf = encryptionService.decryptPDF(data.get().pdfContent(), password, data.get().salt());
            return Optional.of(pdf);
        }else {
            return Optional.empty();
        }
    }
}