package com.example.AutomatedWebNavigation.controller;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/navigationPasaporte")
public class PasaporteWebNavigationController {

    @Autowired
    WebDriver driver;

    @PostMapping
    public void seleniumPrueba(@RequestBody Map<String, String> persona) throws InterruptedException {

        boolean validacion = true;

        while(validacion){

            driver.get("https://santander.gov.co/loader.php?lServicio=Pasaporte&lFuncion=pagoPasaporte");
            if(!driver.getCurrentUrl().equals("https://santander.gov.co/loader.php?lServicio=Pasaporte&lFuncion=pagoPasaporte")){
                System.out.println("No entro aaaaaaaaa");
                Thread.sleep(1000); //Espera 1 segundos
            }else {
                WebElement inputNumeroIdentidadModal = driver.findElement(By.id("identificacionContribuyente"));
                inputNumeroIdentidadModal.sendKeys(persona.get("numeroIdentificacion")); ///numero de documento
                WebElement btnVerificar = driver.findElements(By.className("btnModalVerificar")).get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnVerificar);
                llenarCamposFormulario(persona, driver);
                validacion = false;
            }




        }
        // Navega a la URL

        /*
        WebElement btn = driver.findElements(By.className("btn-primary")).get(1);
        System.out.println("test: " + btn.getText() + " " +
                btn.getTagName());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
         */

        // Espera opcionalmente (por ejemplo, para que la página se cargue completamente)
        //Thread.sleep(30000); // Espera 5 segundos

        // Cierra el navegador al finalizar
        driver.quit();
    }

    private void llenarCamposFormulario(Map<String, String> persona, WebDriver driver) throws InterruptedException {

        WebElement selectTipoDocumentoFormulario =  driver.findElement(By.id("tipo_ide"));
        Select selectTipoDocumento = new Select(selectTipoDocumentoFormulario);
        selectTipoDocumento.selectByValue("TI");

        WebElement inputNumeroIdentidadFormulario = driver.findElement(By.id("num_ide"));
        inputNumeroIdentidadFormulario.sendKeys(persona.get("numeroIdentificacion"));
        WebElement inputNumeroIdentidadConfirmacionFormulario = driver.findElement(By.id("num_ide_confirm"));
        inputNumeroIdentidadConfirmacionFormulario.sendKeys(persona.get("numeroIdentificacion"));

        WebElement nombre = driver.findElement(By.id("nombre"));
        nombre.sendKeys(persona.get("nombre"));
        WebElement nombreConfirm = driver.findElement(By.id("nombre_confirm"));
        nombreConfirm.sendKeys(persona.get("nombre"));
        System.out.println(nombre.getAttribute("placeholder"));

        WebElement apellido = driver.findElement(By.id("apellido"));
        apellido.sendKeys(persona.get("apellido"));
        WebElement apellidoConfirm = driver.findElement(By.id("apellido_confirm"));
        apellidoConfirm.sendKeys(persona.get("apellido"));
        System.out.println(apellido.getAttribute("placeholder"));

        Thread.sleep(10000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        System.out.println(email.getAttribute("placeholder"));
        //Se eliminan atributos que al parecer no permiten que selenium setee el email
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", email);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", email);
        //Se pasa el email
        email.sendKeys(persona.get("email"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", email, persona.get("email"));
        //Se vuelven a agregar los atributos eliminados
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", email);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", email);


        WebElement emailConfirm = driver.findElement(By.id("email_confirm"));
        //Se eliminan atributos que al parecer no permiten que selenium setee el email
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", emailConfirm);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", emailConfirm);
        //Se pasa el email
        emailConfirm.sendKeys(persona.get("email"));
        //Se vuelven a agregar los atributos eliminados
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", emailConfirm);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", emailConfirm);
        System.out.println(emailConfirm.getAttribute("placeholder"));

        //Select
        WebElement selectTipoPasaporteFormulario =  driver.findElements(By.tagName("select")).get(1); //el segundo select
        Select selectTipoPasaporte = new Select(selectTipoPasaporteFormulario);
        selectTipoPasaporte.selectByValue("1");

        WebElement celular = driver.findElements(By.tagName("input")).get(10);

        System.out.println("input relativo: " +  driver.findElement(RelativeLocator.with(By.tagName("input")).above(driver.findElement(By.id("p_mobile")))).getAttribute("placeholder"));
        System.out.println(celular.getAttribute("placeholder"));

        //Se eliminan atributos que al parecer no permiten que selenium setee el email
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", celular);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", celular);
        //Se pasa el celular
        celular.sendKeys(persona.get("celular"));
        //Se vuelven a agregar los atributos eliminados
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", celular);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", celular);

        WebElement btnAceptar = driver.findElement(By.id("acepto"));
        if(!btnAceptar.isSelected()) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAceptar);

        WebElement btnContinuar = driver.findElements(By.className("submit")).get(0);
        System.out.println(btnContinuar.getAttribute("value"));
        if(!btnContinuar.isSelected() && btnContinuar.getAttribute("value").equals("Continuar")) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnContinuar);

        Thread.sleep(30000);
    }
}
