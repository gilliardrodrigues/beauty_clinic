package com.ubi.beautyClinic.adapters.inbound.entities;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPOINTMENT")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "The date and time are required!")
    @Future(message = "The date must be in the future!")
    private OffsetDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required!")
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Service is required!")
    private ServiceEnum service;

    //@Min(value = 1, message = "O valor da avaliação não pode ser menor que 1.")
    //@Max(value = 5, message = "O valor da avaliação não pode ser maior que 5.")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "professionalId", nullable = false)
    private ProfessionalEntity professional;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    private PatientEntity patient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppointmentEntity that = (AppointmentEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
