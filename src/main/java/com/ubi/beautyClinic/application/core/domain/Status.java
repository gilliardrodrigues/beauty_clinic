package com.ubi.beautyClinic.application.core.domain;

public enum Status {

    SOLICITADO("Solicitado"),
    RECUSADO("Recusado"),
    A_REALIZAR("A realizar"),
    REALIZADO("Realizado");

    private final String description;

    Status(String description) {

        this.description = description;
    }
}
