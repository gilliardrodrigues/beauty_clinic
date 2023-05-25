package com.ubi.beautyClinic.adapters.inbound.entities;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalEntity extends UserEntity {

    @NotBlank(message = "O campo 'summary' é obrigatório.")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "professional_services")
    @Column(name = "service")
    @Enumerated(EnumType.STRING)
    private List<ServiceEnum> offeredServices;

    @NotNull(message = "O campo 'consultationPrice' é obrigatório.")
    private BigDecimal consultationPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professional")
    @ToString.Exclude
    private List<AppointmentEntity> appointments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProfessionalEntity that = (ProfessionalEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
