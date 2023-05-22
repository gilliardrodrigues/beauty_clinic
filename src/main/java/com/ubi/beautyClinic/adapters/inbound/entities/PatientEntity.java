package com.ubi.beautyClinic.adapters.inbound.entities;

import com.ubi.beautyClinic.application.core.domain.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity extends UserEntity {

    @NotNull(message = "O campo 'birthDate' é obrigatório.")
    @PastOrPresent(message = "A data de nascimento não pode ser uma data futura.")
    private OffsetDateTime birthDate;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "O campo 'gender' é obrigatório.")
    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    @ToString.Exclude
    private List<AppointmentEntity> appointments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PatientEntity that = (PatientEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
