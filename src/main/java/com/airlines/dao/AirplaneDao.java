package com.airlines.dao;

import com.airlines.model.Airplane;
import com.airlines.model.Crew;

import java.util.List;
import java.util.Optional;

public interface AirplaneDao {

    Optional<Airplane> save(Airplane airplane);

    List<Airplane> findAll();

    Optional<Airplane> findByCodeName(String codeName);

    void remove(Long id);

    Optional<Airplane> findByCrewName(String crewName);

    void update(Airplane airplane, Crew crew);
}
