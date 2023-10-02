package com.example.datamicroserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.example.datamicroserv", "com.example.models.repositories"})
@EntityScan(basePackages = "com.example.models.models.modelsDB")
public class DataMicroServApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataMicroServApplication.class, args);
	}

}
