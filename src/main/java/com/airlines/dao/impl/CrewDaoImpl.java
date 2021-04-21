package com.airlines.dao.impl;

import com.airlines.dao.CrewDao;
import com.airlines.model.Crew;
import com.airlines.model.CrewMember;

import java.util.List;

public class CrewDaoImpl implements CrewDao {
    @Override
    public void add(CrewMember crewMember, Crew crew) {

    }

    @Override
    public List<CrewMember> getByCrewId(long id) {
        return null;
    }

    @Override
    public List<CrewMember> getByCrewName(String name) {
        return null;
    }
}
