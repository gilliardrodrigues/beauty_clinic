package com.ubi.beautyClinic.adapters.outbound.JpaRepositories;

import com.ubi.beautyClinic.adapters.inbound.entities.AppointmentEntity;
import com.ubi.beautyClinic.adapters.inbound.entities.PatientEntity;
import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findAllByPatient(PatientEntity patient);
    List<AppointmentEntity> findAllByProfessional(ProfessionalEntity professional);
    List<AppointmentEntity> findByDateTimeBetween(OffsetDateTime startOfDay, OffsetDateTime endOfDay);
    List<AppointmentEntity> findAllByDateTime(OffsetDateTime datetime);
    List<AppointmentEntity> findAllByService(ServiceEnum service);
    List<AppointmentEntity> findAllByStatus(Status status);
    List<AppointmentEntity> findAllByRating(Integer rating);
}
