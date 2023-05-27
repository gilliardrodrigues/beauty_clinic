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

    public String getDescription() {

        return description;
    }
    @JsonValue
    @Override
    public String toString() {

        return description;
    }
}
