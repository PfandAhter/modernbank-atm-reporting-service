package com.modernbank.atm_reporting_service.websocket.service.cache;


import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.model.entity.EncryptedPDF;
import com.modernbank.atm_reporting_service.model.record.PDFContentData;
import com.modernbank.atm_reporting_service.repository.EncryptedPDFRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ATMReportCacheService extends PDFCacheService{

    @Autowired
    private RedisTemplate<String, PDFContentData> redisTemplate;

    @Autowired
    private EncryptedPDFRepository pdfRepository;

    @Override
    public void cachePDF(String requestId, PDFContentData pdfContent){
        super.cachePDF(requestId,pdfContent);

        redisTemplate.opsForValue().set(
                requestId,
                pdfContent,
                Duration.ofDays(7)
        );
    }

    @Override
    public Optional<PDFContentData> getPDF (String requestId){
        try {
            //oncelikle local cache aranir
            log.info("Getting PDF with requestId: {}", requestId);
            Optional<PDFContentData> localCached = super.getPDF(requestId);

            if (localCached.isPresent()) {
                return localCached;
            }

            Optional<EncryptedPDF> pdf = pdfRepository.findByRequestId(requestId);
            pdf.orElseThrow(() -> new NotFoundException("PDF_GET_FAILED_OR_NOT_FOUND"));

            if (pdf.isPresent()) {
                return Optional.ofNullable(
                        new PDFContentData(pdf.get().getPdf(), pdf.get().getSalt())
                );
            }
        }catch (Exception e){
            log.error("ERROR_OCCURRED_WHILE_GETTING_PDF: {}", e.getMessage());
        }

        return null;
    }

    @Override
    public Map<String,PDFContentData> getAllPDF(){
        return super.getAllPDF();
    }
}