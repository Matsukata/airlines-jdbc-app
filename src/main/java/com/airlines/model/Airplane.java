package com.airlines.model;

import java.time.LocalDate;

public class Airplane {
    private Long id;
    private String codeName;
    private String model;
    private LocalDate manufactureDate;
    private int capacity;
    private int flightRange;
    private CrewMember crew;

    public Airplane() {
    }

    public Airplane(Long id, String codeName, String model, LocalDate manufactureDate, int capacity, int flightRange, CrewMember crew) {
        this.id = id;
        this.codeName = codeName;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.capacity = capacity;
        this.flightRange = flightRange;
        this.crew = crew;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(int flightRange) {
        this.flightRange = flightRange;
    }

    public CrewMember getCrew() {
        return crew;
    }

    public void setCrew(CrewMember crew) {
        this.crew = crew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airplane airplane = (Airplane) o;

        if (capacity != airplane.capacity) return false;
        if (flightRange != airplane.flightRange) return false;
        if (id != null ? !id.equals(airplane.id) : airplane.id != null) return false;
        if (codeName != null ? !codeName.equals(airplane.codeName) : airplane.codeName != null) return false;
        if (model != null ? !model.equals(airplane.model) : airplane.model != null) return false;
        if (manufactureDate != null ? !manufactureDate.equals(airplane.manufactureDate) : airplane.manufactureDate != null)
            return false;
        return crew != null ? crew.equals(airplane.crew) : airplane.crew == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codeName != null ? codeName.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (manufactureDate != null ? manufactureDate.hashCode() : 0);
        result = 31 * result + capacity;
        result = 31 * result + flightRange;
        result = 31 * result + (crew != null ? crew.hashCode() : 0);
        return result;
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
                ", crew=" + crew +
                '}';
    }
}