package com.airlines.dao;

import com.airlines.model.Airplane;
import com.airlines.model.CrewMember;

public interface CrewMemberDao {
    void save(CrewMember crew);

    CrewMember findOne(Long id);

    CrewMember link(Airplane airplane, Long id);

    CrewMember update(CrewMember crewMember);
}
