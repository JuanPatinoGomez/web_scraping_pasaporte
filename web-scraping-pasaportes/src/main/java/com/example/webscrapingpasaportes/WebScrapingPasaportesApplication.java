package com.example.webscrapingpasaportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/web-scraping")
public class WebScrapingPasaportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebScrapingPasaportesApplication.class, args);
	}

}
