package com.airlines.model;

import java.time.LocalDate;
import java.util.Objects;

public class CrewMember {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Position position;
    private final LocalDate birthday;
    private final Citizenship citizenship;

    public CrewMember(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.position = builder.position;
        this.birthday = builder.birthday;
        this.citizenship = builder.citizenship;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewMember that = (CrewMember) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && position == that.position && Objects.equals(birthday, that.birthday) && citizenship == that.citizenship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, position, birthday, citizenship);
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Position position;
        private LocalDate birthday;
        private Citizenship citizenship;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withCitizenship(Citizenship citizenship) {
            this.citizenship = citizenship;
            return this;
        }

        public CrewMember build() {
            return new CrewMember(this);
        }
    }
}
