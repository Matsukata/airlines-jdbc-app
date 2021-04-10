package com.app.dao;

import com.app.model.Airplane;
import com.app.model.Crew;

import java.util.List;

public interface CrewDao {
    void save(Crew crew);

    Crew findOne(Long id);

    Crew link(Airplane airplane, Long id);
}
