package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceGeneratorManager{

    private final Map<String, IInvoiceGenerationStrategy> strategyMap = new HashMap<>();

    public InvoiceGeneratorManager (List<IInvoiceGenerationStrategy> strategies){
        for (IInvoiceGenerationStrategy strategy : strategies) {
            strategyMap.put(strategy.generationType(), strategy);
        }
    }


    public byte[] generate(String generationType, Map<String,Object> data){
        IInvoiceGenerationStrategy strategy = strategyMap.get(generationType);

        if(strategy == null){
            throw new IllegalArgumentException("No strategy found for generation type: " + generationType);
        }

        return strategy.generatePDF(data);
    }

    public List<String> getAvailableStrategies(){
        return strategyMap.keySet().stream().toList();
    }
}