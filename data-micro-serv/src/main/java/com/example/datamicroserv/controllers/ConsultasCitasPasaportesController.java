package com.example.datamicroserv.controllers;


import com.example.models.models.modelsDB.PersonasPasaportes;
import com.example.models.repositories.ConsultasCitasPasaportesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultasCitasPasaportes")
public class ConsultasCitasPasaportesController {

    @Autowired
    private ConsultasCitasPasaportesRepositories repository;

    @GetMapping()
    public List<PersonasPasaportes> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public PersonasPasaportes findById(@PathVariable Integer id){
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/enabled")
    public List<PersonasPasaportes> findAllByIndicadorHabilitadoIsTrue(){
        return repository.findAllByIndicadorHabilitadoIsTrue();
    }

    @PostMapping()
    public PersonasPasaportes create(@RequestBody PersonasPasaportes o){
        o.setActivo(o.getActivo() != null ? o.getActivo() : true);
        o.setFechaUltimaModificacion(LocalDate.now());
        return repository.save(o);
    }




}
