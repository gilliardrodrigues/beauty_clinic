package com.ubi.beautyClinic.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

    private OffsetDateTime birthDate;
    private Gender gender;
    private List<Appointment> appointments;
}
