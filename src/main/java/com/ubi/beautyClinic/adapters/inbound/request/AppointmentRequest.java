package com.ubi.beautyClinic.adapters.inbound.request;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
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

    @NotNull(message = "O profissional é obrigatório!")
    private Long professionalId;

    @NotNull(message = "A data e o horário são obrigatórios!")
    @Future(message = "A data deve estar no futuro!")
    private OffsetDateTime dateTime;

    @NotNull(message = "O serviço é obrigatório!")
    private ServiceEnum service;
}
