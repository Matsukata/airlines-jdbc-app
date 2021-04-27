package com.airlines.dao;

import com.airlines.model.CrewMember;

import java.util.Optional;

public interface CrewMemberDao {
    Optional<CrewMember> save(CrewMember crew);

    Optional<CrewMember> findById(Long id);

    void update(CrewMember crewMember);
}
