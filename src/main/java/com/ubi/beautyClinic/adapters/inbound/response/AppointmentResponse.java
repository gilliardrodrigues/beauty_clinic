package com.ubi.beautyClinic.adapters.inbound.response;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AppointmentResponse {

    private Long id;
    private OffsetDateTime dateTime;
    private Status status;
    private ServiceEnum service;
    private Integer rating;
    private String professionalFullName;
    private String patientFullName;
}
