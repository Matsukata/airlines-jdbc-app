package com.airlines.model;

import java.time.LocalDate;
import java.util.Objects;

public class Airplane {
    private final Long id;
    private final String codeName;
    private final String model;
    private final LocalDate manufactureDate;
    private final int capacity;
    private final int flightRange;
    private final CrewMember crewMember;

    public Airplane(Builder builder) {
        this.id = builder.id;
        this.codeName = builder.codeName;
        this.model = builder.model;
        this.manufactureDate = builder.manufactureDate;
        this.capacity = builder.capacity;
        this.flightRange = builder.flightRange;
        this.crewMember = builder.crewMember;
    }

    public Long getId() {
        return id;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlightRange() {
        return flightRange;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return capacity == airplane.capacity && flightRange == airplane.flightRange && Objects.equals(id, airplane.id) && Objects.equals(codeName, airplane.codeName) && Objects.equals(model, airplane.model) && Objects.equals(manufactureDate, airplane.manufactureDate) && Objects.equals(crewMember, airplane.crewMember);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeName, model, manufactureDate, capacity, flightRange, crewMember);
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", codeName='" + codeName + '\'' +
                ", model='" + model + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", capacity=" + capacity +
                ", flightRange=" + flightRange +
                ", crew=" + crewMember +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String codeName;
        private String model;
        private LocalDate manufactureDate;
        private int capacity;
        private int flightRange;
        private CrewMember crewMember;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withCodeName(String codeName) {
            this.codeName = codeName;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withManufactureDate(LocalDate manufactureDate) {
            this.manufactureDate = manufactureDate;
            return this;
        }

        public Builder withCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder withFlightRange(int flightRange) {
            this.flightRange = flightRange;
            return this;
        }

        public Builder withCrewMember(CrewMember crewMember) {
            this.crewMember = crewMember;
            return this;
        }

        public Airplane build() {
            return new Airplane(this);
        }
    }
}
