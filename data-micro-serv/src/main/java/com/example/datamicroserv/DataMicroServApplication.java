package com.example.datamicroserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/data")
public class DataMicroServApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataMicroServApplication.class, args);
	}

}
