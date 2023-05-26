package com.ubi.beautyClinic.application.ports.out;

import com.ubi.beautyClinic.application.core.domain.*;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepositoryOutboundPort {

    Appointment save(Appointment appointment) throws BusinessLogicException;
    void delete(Long id);
    Appointment findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByProfessional(Professional professional);
    List<Appointment> findByDate(OffsetDateTime startOfDay, OffsetDateTime endOfDay);
    List<Appointment> findByDateTime(OffsetDateTime datetime);
    List<Appointment> findByService(ServiceEnum service);
    List<Appointment> findByStatus(Status status);
    List<Appointment> findByRating(Integer rating);
}
