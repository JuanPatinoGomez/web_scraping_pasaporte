package com.example.AutomatedWebNavigation.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfig {

    @Bean
    public WebDriver driver(){
        // Configura el controlador de Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Juan\\OneDrive\\Documents\\driver_google_chromer\\chromedriver-win64\\chromedriver.exe");
        // Opcional: Configura opciones de Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Abre el navegador maximizado
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        //options.addArguments("--headless");
        // Inicializa el controlador de Selenium para Chrome con las opciones configuradas
        return new ChromeDriver(options);
    }
}
