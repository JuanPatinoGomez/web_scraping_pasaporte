package com.example.models.repositories;

import com.example.models.models.modelsDB.PersonasPasaportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultasCitasPasaportesRepositories extends JpaRepository<PersonasPasaportes, Integer> {

    List<PersonasPasaportes> findAllByIndicadorHabilitadoIsTrue();
}
