package com.airlines.dao;

import com.airlines.model.Airplane;
import com.airlines.model.Crew;

import java.util.List;

public interface AirplaneDao {

    void save(Airplane airplane);

    List<Airplane> findAll();

    Airplane findOne(String codeName);

    void remove(Airplane airplane);

    Airplane findByCrewName(String crewName);

    void update(Airplane airplane, Crew crew);
}
