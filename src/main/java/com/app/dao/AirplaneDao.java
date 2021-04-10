package com.app.dao;

import com.app.model.Airplane;
import com.app.model.Crew;

import java.util.List;

public interface AirplaneDao {

    void save(Airplane airplane);

    List<Airplane> findAll();

    Airplane findOne(String codeName);

    void remove(Airplane airplane);

    Airplane findOne(Crew firstName, Crew lastName);
}
