package com.ubi.beautyClinic.adapters.inbound.request;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "Professional is required!")
    private Long professionalId;

    @NotNull(message = "The date and time are required!")
    @Future(message = "The date must be in the future!")
    private OffsetDateTime dateTime;

    @NotNull(message = "Service is required!")
    @Enumerated(EnumType.STRING)
    private ServiceEnum service;
}
