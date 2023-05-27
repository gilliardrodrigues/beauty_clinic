package com.ubi.beautyClinic.application.core.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    REQUESTED("Requested"),
    REFUSED("Refused"),
    TO_ACCOMPLISH("To Accomplish"),
    ACCOMPLISHED("Accomplished");

    private final String description;

    Status(String description) {

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
