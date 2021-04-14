package com.airlines.dao;

import com.airlines.model.Crew;
import com.airlines.model.CrewMember;

import java.util.List;

public interface CrewDao {
    void add(CrewMember crewMember, Crew crew);

    List<CrewMember> getByCrewId(long id);

    List<CrewMember> getByCrewName(String name);
}
