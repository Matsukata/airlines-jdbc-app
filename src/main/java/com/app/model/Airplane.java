package com.app.model;

import java.time.LocalDate;

public class Airplane {
    private Long id;
    private String codeName;
    private String model;
    private LocalDate manufactureDate;
    private int capacity;
    private int flightRange;

    public Airplane() {
    }

    public Airplane(Long id, String codeName, String model, LocalDate manufactureDate, int capacity, int flightRange) {
        this.id = id;
        this.codeName = codeName;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.capacity = capacity;
        this.flightRange = flightRange;
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
}
