package com.airlines.dao;

import com.airlines.model.Airplane;
import com.airlines.model.Crew;

import java.util.List;
import java.util.Optional;

public interface AirplaneDao {

    Optional<Airplane> save(Airplane airplane);

    List<Airplane> findAll();

    Airplane findOne(String codeName);

    void remove(Airplane airplane);

    Airplane findByCrewName(String crewName);

    Airplane update(Crew crew);
}
