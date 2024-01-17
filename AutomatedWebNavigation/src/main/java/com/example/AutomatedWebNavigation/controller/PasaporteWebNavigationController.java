package com.example.AutomatedWebNavigation.controller;

import com.example.models.models.modelsOut.PersonasPasaportes;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/navigationPasaporte")
public class PasaporteWebNavigationController {

    @Autowired
    WebDriver driver;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/excellPasaportes")
    public void seleniumPrueba() throws InterruptedException {
        WebDriver driverU = driver;
        LocalTime horaFinalizaPrimerLanzamiento = LocalTime.of(8, 7);
        LocalTime horaFinalizaSegundoLanzamiento = LocalTime.of(10, 7);

        List<PersonasPasaportes> personasPasaportesList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/EXCELL-MICRO/api/excell/leerPersonasPasaportes", PersonasPasaportes[].class)));
        if(personasPasaportesList.isEmpty()) return;

        outerLoop:
        for (PersonasPasaportes personasPasaportes : personasPasaportesList) {
            if(!validarCamposPersona(personasPasaportes)) continue; //si falta algun campo que no continue

            boolean validacion = true;

            while(validacion){

               driverU = navegacionPaginaPrincipal(driverU);

                if(!driverU.getCurrentUrl().equals("https://santander.gov.co/loader.php?lServicio=Pasaporte&lFuncion=pagoPasaporte")){
                    System.out.println("No entro aaaaaaaaa");
                    Thread.sleep(1000); //Espera 1 segundos
                }else {
                    WebElement inputNumeroIdentidadModal = driverU.findElement(By.id("identificacionContribuyente"));
                    inputNumeroIdentidadModal.sendKeys(personasPasaportes.getNumeroDocumento()); ///numero de documento
                    WebElement btnVerificar = driverU.findElements(By.className("btnModalVerificar")).get(0);
                    ((JavascriptExecutor) driverU).executeScript("arguments[0].click();", btnVerificar);
                    System.out.println(personasPasaportes.toString());
                    try{
                        llenarCamposFormulario(personasPasaportes, driverU);
                    } catch (Exception e) {
                        e.printStackTrace(); //Si se llega a estallar por algún motivo que intente continuar con el siguiente
                        continue outerLoop;

                    }

                    validacion = false;
                }
                LocalTime tiempoActual = LocalTime.now();
                LocalTime horaYMinActual = LocalTime.of(tiempoActual.getHour(), tiempoActual.getMinute());
                //Si ya es igual a alguna de esas horas que se salga del bucle
                if(horaYMinActual.equals(horaFinalizaPrimerLanzamiento) || horaYMinActual.equals(horaFinalizaSegundoLanzamiento) /*|| horaYMinActual.isAfter(horaFinalizaPrimerLanzamiento) || horaYMinActual.isAfter(horaFinalizaSegundoLanzamiento)*/) break outerLoop;
            };

        }


        // Cierra el navegador al finalizar
        driverU.quit();

        //Se manda a hacer la escritura en el excell
        restTemplate.postForObject("http://localhost:8088/EXCELL-MICRO/api/excell/escribirLinks", personasPasaportesList, void.class);
    }

    private WebDriver navegacionPaginaPrincipal(WebDriver driver) {
        try{
            driver.get("https://santander.gov.co/loader.php?lServicio=Pasaporte&lFuncion=pagoPasaporte");
            return driver;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Se cerro el navegador y se intentara abrir de nuevo para ejecutar las acciones.");
            driver.quit();
            // Configura el controlador de Chrome
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Juan\\OneDrive\\Documents\\driver_google_chromer\\chromedriver-win64\\chromedriver.exe");
            // Opcional: Configura opciones de Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Abre el navegador maximizado
            options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
            //options.addArguments("--headless");
            // Inicializa el controlador de Selenium para Chrome con las opciones configuradas
            WebDriver driverNew =  new ChromeDriver(options);
            driverNew.get("https://santander.gov.co/loader.php?lServicio=Pasaporte&lFuncion=pagoPasaporte");
            return driverNew;
        }
    }

    private Boolean validarCamposPersona(PersonasPasaportes personasPasaportes) {

        return personasPasaportes != null && !personasPasaportes.getNombres().isEmpty() && !personasPasaportes.getNombres().isBlank()
                && !personasPasaportes.getApellidos().isEmpty() && !personasPasaportes.getApellidos().isBlank()
                && !personasPasaportes.getTipoDocumento().isEmpty() && !personasPasaportes.getTipoDocumento().isBlank()
                && !personasPasaportes.getNumeroDocumento().isEmpty() && !personasPasaportes.getNumeroDocumento().isBlank()
                && !personasPasaportes.getNumeroCelular().isEmpty() && !personasPasaportes.getNumeroCelular().isBlank()
                && !personasPasaportes.getEmail().isEmpty() && !personasPasaportes.getEmail().isBlank()
                ;
    }

    private void llenarCamposFormulario(PersonasPasaportes personasPasaportes, WebDriver driver) throws InterruptedException {

        WebElement selectTipoDocumentoFormulario =  driver.findElement(By.id("tipo_ide"));
        Select selectTipoDocumento = new Select(selectTipoDocumentoFormulario);
        int intentos = 0;

        //Verificar si ya precargo informacion

        if(selectTipoDocumento.getAllSelectedOptions().isEmpty() || (!(selectTipoDocumento.getAllSelectedOptions().isEmpty() && selectTipoDocumento.getFirstSelectedOption().getText().equals("Seleccione...")))){
            selectTipoDocumento.selectByValue(personasPasaportes.getTipoDocumento());

            llenarNumDocumento(personasPasaportes, driver);

            llenarNombre(personasPasaportes, driver);

            llenarApellido(personasPasaportes, driver);
        }


        llenarEmail(personasPasaportes, driver);


        llenarTipoPasaporte(driver);

        llenarCelular(personasPasaportes, driver);

        WebElement btnAceptar = driver.findElement(By.id("acepto"));
        while(!(btnAceptar.isEnabled() && btnAceptar.isDisplayed())){
            btnAceptar = driver.findElement(By.id("acepto"));
            System.out.println("while del btn aceptar");
        }
        if(!btnAceptar.isSelected()) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnAceptar);

        WebElement btnContinuar = driver.findElements(By.className("submit")).get(0);
        while(!(btnContinuar.isEnabled() && btnContinuar.isDisplayed())){
            btnContinuar = driver.findElements(By.className("submit")).get(0);
            System.out.println("while del submit");
        }
        System.out.println(btnContinuar.getAttribute("value"));
        if(!btnContinuar.isSelected() && btnContinuar.getAttribute("value").equals("Continuar")) ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnContinuar);

        //Thread.sleep(50000);
        personasPasaportes.setLink(driver.getCurrentUrl()); //Revisar como lo toma
        System.out.println(personasPasaportes.getLink());
        //Thread.sleep(10000);
        System.out.println();
    }

    private void llenarCelular(PersonasPasaportes personasPasaportes, WebDriver driver) {
        int contadorCelular = 0;
        WebElement celular = driver.findElements(By.tagName("input")).get(10);
        while(!(celular.isEnabled() && celular.isDisplayed())){
            contadorCelular++;
            if(contadorCelular%2 == 0) { //Cuando sea impar revisa el 10 de lo contrario el 11
                celular = driver.findElements(By.tagName("input")).get(10);
            }else {
                celular = driver.findElements(By.tagName("input")).get(11);
            }
            System.out.println("while del celular");
        }
        //System.out.println("input relativo: " +  driver.findElement(RelativeLocator.with(By.tagName("input")).above(driver.findElement(By.id("p_mobile")))).getAttribute("placeholder"));
        System.out.println(celular.getAttribute("placeholder"));
        //Se eliminan atributos que al parecer no permiten que selenium setee el email
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", celular);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", celular);
        //Se pasa el celular
        celular.sendKeys(personasPasaportes.getNumeroCelular());
        //Se vuelven a agregar los atributos eliminados
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", celular);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", celular);
    }

    private void llenarTipoPasaporte(WebDriver driver) {
        //Select
        WebElement selectTipoPasaporteFormulario =  driver.findElements(By.tagName("select")).get(1); //el segundo select
        Select selectTipoPasaporte = new Select(selectTipoPasaporteFormulario);
        selectTipoPasaporte.selectByValue("1");
    }

    private void llenarEmail(PersonasPasaportes personasPasaportes, WebDriver driver) {
        WebElement email = driver.findElement(By.id("email"));
        while(!(email.isEnabled() && email.isDisplayed())){
            email = driver.findElement(By.id("email"));
            System.out.println("while del email");
        }
        System.out.println(email.getAttribute("placeholder"));
        //Se eliminan atributos que al parecer no permiten que selenium setee el email
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", email);
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", email);
        //Se pasa el email
        email.sendKeys(personasPasaportes.getEmail());
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", email, personasPasaportes.getEmail()); //creo que esta y la linea de arriba se puede elimnar
        //Se vuelven a agregar los atributos eliminados
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", email);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", email);


        WebElement emailConfirm = driver.findElement(By.id("email_confirm"));
        while(!(emailConfirm.isEnabled() && emailConfirm.isDisplayed())){
            emailConfirm = driver.findElement(By.id("email_confirm"));
            System.out.println("while del email confirm");
        }
        if(emailConfirm.isEnabled() && emailConfirm.isDisplayed()){
            //Se eliminan atributos que al parecer no permiten que selenium setee el email
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('oncopy')", emailConfirm);
            ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('onpaste')", emailConfirm);
            //Se pasa el email
            emailConfirm.sendKeys(personasPasaportes.getEmail());
            //Se vuelven a agregar los atributos eliminados
            ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('oncopy', 'return false')", emailConfirm);
            ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('onpaste', 'return false')", emailConfirm);
            System.out.println(emailConfirm.getAttribute("placeholder"));
        }
    }

    private void llenarApellido(PersonasPasaportes personasPasaportes, WebDriver driver) {
        int intentos;
        WebElement apellido = driver.findElement(By.id("apellido"));
        while(!(apellido.isEnabled() && apellido.isDisplayed())){
            apellido = driver.findElement(By.id("apellido"));
            System.out.println("while del apellido");
        }
        apellido.sendKeys(personasPasaportes.getApellidos());

        intentos = 0;
        WebElement apellidoConfirm = driver.findElement(By.id("apellido_confirm"));
        while(!(apellidoConfirm.isEnabled() && apellidoConfirm.isDisplayed()) && intentos != 5){
            apellidoConfirm = driver.findElement(By.id("apellido_confirm"));
            System.out.println("while del apellido confirm");
        }
        if(apellidoConfirm.isEnabled() && apellidoConfirm.isDisplayed()) apellidoConfirm.sendKeys(personasPasaportes.getApellidos());
        System.out.println(apellido.getAttribute("placeholder"));
    }

    private void llenarNombre(PersonasPasaportes personasPasaportes, WebDriver driver) {
        int intentos;
        WebElement nombre = driver.findElement(By.id("nombre"));
        while(!(nombre.isEnabled() && nombre.isDisplayed())){
            nombre = driver.findElement(By.id("nombre"));
            System.out.println("while del nombre");
        }
        nombre.sendKeys(personasPasaportes.getNombres());

        intentos = 0;
        WebElement nombreConfirm = driver.findElement(By.id("nombre_confirm"));
        while(!(nombreConfirm.isEnabled() && nombreConfirm.isDisplayed()) && intentos != 5){
            nombreConfirm = driver.findElement(By.id("nombre_confirm"));
            System.out.println("while del nombre confirm");
            intentos++;
        }
        if(nombreConfirm.isEnabled() && nombreConfirm.isDisplayed()) nombreConfirm.sendKeys(personasPasaportes.getNombres());
        System.out.println(nombre.getAttribute("placeholder"));
    }

    private void llenarNumDocumento(PersonasPasaportes personasPasaportes, WebDriver driver) {
        WebElement inputNumeroIdentidadFormulario = driver.findElement(By.id("num_ide"));
        while(!(inputNumeroIdentidadFormulario.isEnabled() && inputNumeroIdentidadFormulario.isDisplayed())){
            inputNumeroIdentidadFormulario = driver.findElement(By.id("num_ide"));
            System.out.println("while del numerodoc");
        }
        inputNumeroIdentidadFormulario.sendKeys(personasPasaportes.getNumeroDocumento());

        WebElement inputNumeroIdentidadConfirmacionFormulario = driver.findElement(By.id("num_ide_confirm"));
        while(!(inputNumeroIdentidadConfirmacionFormulario.isEnabled() && inputNumeroIdentidadConfirmacionFormulario.isDisplayed())){
            inputNumeroIdentidadConfirmacionFormulario = driver.findElement(By.id("num_ide_confirm"));
            System.out.println("while del numerodoc confirm");
        }
        inputNumeroIdentidadConfirmacionFormulario.sendKeys(personasPasaportes.getNumeroDocumento());
    }
}
