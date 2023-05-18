package com.ubi.beautyClinic.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Professional extends User {

    private String summary;
    private List<ServiceType> offeredServices;
    private BigDecimal consultationPrice;
    private List<Appointment> appointments;
}
