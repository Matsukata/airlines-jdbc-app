package com.airlines.dao;

import com.airlines.model.Airplane;

import java.util.List;
import java.util.Optional;

public interface AirplaneDao {

    Optional<Airplane> save(Airplane airplane);

    List<Airplane> findAll();

    Optional<Airplane> findByCodeName(String codeName);

    void removeById(Long airplaneId);

    Optional<Airplane> findByCrewName(String crewName);

    void updateWithCrewId(Airplane airplane, Long crewId);
}
