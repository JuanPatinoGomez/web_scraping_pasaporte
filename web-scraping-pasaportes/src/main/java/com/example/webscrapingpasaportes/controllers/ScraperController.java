package com.example.webscrapingpasaportes.controllers;

import com.example.webscrapingpasaportes.models.ResponseDTO;
import com.example.webscrapingpasaportes.services.ScraperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/scraperConsulta")
public class ScraperController {

    @Autowired
    ScraperServiceImpl scraperService;

    @GetMapping(path = "/consultar/{vehicleModel}")
    public Set<ResponseDTO> getVehicleByModel(@PathVariable String vehicleModel) {
        System.out.println("Entro");
        Set<ResponseDTO> responseDTO = scraperService.getVehicleByModel(vehicleModel);
        return  responseDTO;

    }

    @GetMapping(path = "/saludo")
    public String getVehicleByModel() {
        return "hola";
    }

    @GetMapping("/bogota")
    public void consultarBogota(){
        scraperService.consultarBogota();
    }
}
