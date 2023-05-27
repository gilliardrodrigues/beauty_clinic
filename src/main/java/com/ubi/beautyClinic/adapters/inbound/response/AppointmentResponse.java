package com.ubi.beautyClinic.adapters.inbound.response;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AppointmentResponse {

    private Long id;
    private OffsetDateTime dateTime;
    private String status;
    private String service;
    private Integer rating;
    private String professionalFullName;
    private String patientFullName;
}
