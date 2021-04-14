package com.airlines.model;

import java.util.List;

public class Crew {
    private Long id;
    private String name;
    private List<CrewMember> crewMembers;

    public Crew() {
    }

    public Crew(Long id, String name, List<CrewMember> crewMembers) {
        this.id = id;
        this.name = name;
        this.crewMembers = crewMembers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crew crew = (Crew) o;

        if (id != null ? !id.equals(crew.id) : crew.id != null) return false;
        if (name != null ? !name.equals(crew.name) : crew.name != null) return false;
        return crewMembers != null ? crewMembers.equals(crew.crewMembers) : crew.crewMembers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (crewMembers != null ? crewMembers.hashCode() : 0);
        return result;
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
