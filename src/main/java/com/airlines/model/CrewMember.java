package com.airlines.model;

import java.time.LocalDate;

public class CrewMember {
    private Long id;
    private String firstName;
    private String lastName;
    private Position position;
    private LocalDate birthday;
    private Citizenship citizenship;

    public CrewMember() {
    }

    public CrewMember(Long id, String firstName, String lastName, Position position, LocalDate birthday, Citizenship citizenship) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.birthday = birthday;
        this.citizenship = citizenship;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CrewMember crew = (CrewMember) o;

        if (id != null ? !id.equals(crew.id) : crew.id != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(crew.firstName) : crew.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(crew.lastName) : crew.lastName != null) {
            return false;
        }
        if (position != null ? !position.equals(crew.position) : crew.position != null) {
            return false;
        }
        if (birthday != null ? !birthday.equals(crew.birthday) : crew.birthday != null) {
            return false;
        }
        return citizenship != null ? citizenship.equals(crew.citizenship) : crew.citizenship == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (citizenship != null ? citizenship.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", birthday=" + birthday +
                ", citizenship='" + citizenship + '\'' +
                '}';
    }
}
