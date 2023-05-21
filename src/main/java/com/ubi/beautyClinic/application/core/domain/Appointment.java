package com.ubi.beautyClinic.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    private Long id;
    private OffsetDateTime dateTime;
    private Status status;
    private ServiceEnum service;
    private Integer rating;
    private Professional professional;
    private Patient patient;
}
