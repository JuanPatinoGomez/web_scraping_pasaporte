package com.example.mediatormicroserv.controllers;

import com.example.mediatormicroserv.models.ConsultasCitasPasaportes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/consultasCitasPasaportes")
public class ConsultasCitasPasaportesController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping()
    public List<ConsultasCitasPasaportes> findAll(){
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://data-micro/api/consultasCitasPasaportes", ConsultasCitasPasaportes[].class)));
    }

    @GetMapping("/{id}")
    public ConsultasCitasPasaportes findById(@PathVariable Integer id){
        return restTemplate.getForObject("http://data-micro/api/consultasCitasPasaportes/" + id, ConsultasCitasPasaportes.class);
    }

    @GetMapping("/enabled")
    public List<ConsultasCitasPasaportes> findAllByIndicadorHabilitadoIsTrue(){
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://data-micro/api/consultasCitasPasaportes/enabled", ConsultasCitasPasaportes[].class)));
    }

    @PostMapping()
    public ConsultasCitasPasaportes create(@RequestBody ConsultasCitasPasaportes o){
        return restTemplate.postForObject("http:/data-micro/api/consultasCitasPasaportes", o, ConsultasCitasPasaportes.class);
    }
}
