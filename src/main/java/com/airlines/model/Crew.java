package com.airlines.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Crew {
    private final Long id;
    private final String name;
    private final List<CrewMember> crewMembers;

    Crew(Long id, String name, List<CrewMember> crewMembers) {
        this.id = id;
        this.name = name;
        this.crewMembers = Collections.unmodifiableList(crewMembers);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(id, crew.id) && Objects.equals(name, crew.name) && Objects.equals(crewMembers, crew.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, crewMembers);
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crewMembers=" + crewMembers +
                '}';
    }
}
