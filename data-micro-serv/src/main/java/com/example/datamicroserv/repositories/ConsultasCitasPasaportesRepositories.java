package com.example.datamicroserv.repositories;

import com.example.datamicroserv.models.ConsultasCitasPasaportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultasCitasPasaportesRepositories extends JpaRepository<ConsultasCitasPasaportes, Integer> {

    List<ConsultasCitasPasaportes> findAllByIndicadorHabilitadoIsTrue();
}
