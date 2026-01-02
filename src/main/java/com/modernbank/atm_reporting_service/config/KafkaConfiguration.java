package com.modernbank.atm_reporting_service.config;

import com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConfiguration {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Bean
    public ProducerFactory<String, CreatePDFRequest> pdfProducerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers); // Kafka broker adresi
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, CreatePDFRequest> pdfKafkaTemplate(){
        KafkaTemplate<String, CreatePDFRequest> kafkaTemplate = new KafkaTemplate<>(pdfProducerFactory());
//        kafkaTemplate.setProducerListener(new InvoiceKafkaProducerListener());
        return kafkaTemplate;
    }

    @Bean
    public ConsumerFactory<String, CreatePDFRequest> pdfConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers); // Kafka broker adresi
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "pdf-consumer-group"); // Consumer group ID
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Paket güvenliği
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.modernbank.atm_reporting_service.websocket.controller.api.request.CreatePDFRequest");
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
        return new DefaultKafkaConsumerFactory<>(configProps,new StringDeserializer(),new JsonDeserializer<>(CreatePDFRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CreatePDFRequest> pdfKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, CreatePDFRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pdfConsumerFactory());

        return factory;
    }

}