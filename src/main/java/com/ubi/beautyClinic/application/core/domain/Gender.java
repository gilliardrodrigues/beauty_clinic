package com.ubi.beautyClinic.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String description;

    Gender(String description) {

        this.description = description;
    }

    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.description.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid Gender value: " + value);
    }

    public String getDescription() {

        return description;
    }
    @JsonValue
    @Override
    public String toString() {

        return description;
    }
}
