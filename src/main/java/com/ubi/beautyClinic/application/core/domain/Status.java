package com.ubi.beautyClinic.application.core.domain;

public enum Status {

    REQUESTED("Solicitada"),
    REFUSED("Recusada"),
    TO_ACCOMPLISH("A realizar"),
    ACCOMPLISHED("Realizada");

    private final String description;

    Status(String description) {

        this.description = description;
    }
}
