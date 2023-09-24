package com.example.mediatormicroserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/mediator")
public class MediatorMicroServApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediatorMicroServApplication.class, args);
	}

}
