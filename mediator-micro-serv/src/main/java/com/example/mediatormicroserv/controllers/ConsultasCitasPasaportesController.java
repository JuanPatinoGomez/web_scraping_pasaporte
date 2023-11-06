package com.example.mediatormicroserv.controllers;

import com.example.models.models.modelsOut.PersonasPasaportes;
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
    public List<PersonasPasaportes> findAll(){
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/DATA-MICRO/api/consultasCitasPasaportes", PersonasPasaportes[].class)));
    }

    @GetMapping("/{id}")
    public PersonasPasaportes findById(@PathVariable Integer id){
        return restTemplate.getForObject("http://localhost:8088/DATA-MICRO/api/consultasCitasPasaportes/" + id, PersonasPasaportes.class);
    }

    @GetMapping("/enabled")
    public List<PersonasPasaportes> findAllByIndicadorHabilitadoIsTrue(){
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/DATA-MICRO/api/consultasCitasPasaportes/enabled", PersonasPasaportes[].class)));
    }

    @PostMapping()
    public PersonasPasaportes create(@RequestBody PersonasPasaportes o){
        return restTemplate.postForObject("http://localhost:8088/DATA-MICRO/api/consultasCitasPasaportes", o, PersonasPasaportes.class);
    }
}
