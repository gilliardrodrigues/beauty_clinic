package com.ubi.beautyClinic.application.core.domain;

public enum Gender {

    MALE("Masculino"),
    FEMALE("Feminino"),
    OTHER("Outro");

    private final String description;

    Gender(String description) {

        this.description = description;
    }
}
