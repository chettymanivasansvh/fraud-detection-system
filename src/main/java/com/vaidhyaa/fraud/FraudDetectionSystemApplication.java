package com.vaidhyaa.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FraudDetectionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudDetectionSystemApplication.class, args);
	}

}
