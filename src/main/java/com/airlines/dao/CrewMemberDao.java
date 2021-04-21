package com.airlines.dao;

import com.airlines.model.Airplane;
import com.airlines.model.CrewMember;

public interface CrewMemberDao {
    void save(CrewMember crew);

    CrewMember findById(Long id);

    CrewMember update(CrewMember crewMember);
}
