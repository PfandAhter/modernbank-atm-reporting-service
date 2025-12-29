package com.modernbank.atm_reporting_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
@EnableCaching
public class AtmReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmReportingServiceApplication.class, args);
	}

}
