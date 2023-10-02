package com.example.datamicroserv.controllers;


import com.example.models.models.modelsDB.ConsultasCitasPasaportes;
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
    public List<ConsultasCitasPasaportes> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ConsultasCitasPasaportes findById(@PathVariable Integer id){
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/enabled")
    public List<ConsultasCitasPasaportes> findAllByIndicadorHabilitadoIsTrue(){
        return repository.findAllByIndicadorHabilitadoIsTrue();
    }

    @PostMapping()
    public ConsultasCitasPasaportes create(@RequestBody ConsultasCitasPasaportes o){
        o.setIndicadorHabilitado(o.getIndicadorHabilitado() != null ? o.getIndicadorHabilitado() : true);
        o.setFechaUltimaModificacion(LocalDate.now());
        return repository.save(o);
    }




}
