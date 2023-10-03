package com.example.AutomatedWebNavigation.controller;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/navigationPasaporte")
public class PasaporteWebNavigationController {

    @GetMapping
    public void seleniumPrueba() throws InterruptedException {

        WebDriverManager.chromedriver().browserVersion("brave").setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Brave.lnk");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com");

        Thread.sleep(10000);

        driver.quit();
    }
}
