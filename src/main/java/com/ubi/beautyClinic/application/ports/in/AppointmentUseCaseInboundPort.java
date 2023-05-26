package com.ubi.beautyClinic.application.ports.in;

import com.ubi.beautyClinic.application.core.domain.*;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentUseCaseInboundPort {

    Appointment acceptAppointment(Long id);

    Appointment refuseAppointment(Long id);

    Appointment requestAppointment(Appointment appointment) throws BusinessLogicException;
    void deleteAppointment(Long id);
    Appointment getAppointmentById(Long id);
    List<Appointment> listAppointmentsForLoggedUser();
    List<Appointment> listAppointmentsByDate(OffsetDateTime date);
    List<Appointment> listAppointmentsByDateTime(OffsetDateTime datetime);
    List<Appointment> listAppointmentsByService(ServiceEnum service);
    List<Appointment> listAppointmentsByStatus(Status status);
    List<Appointment> listAppointmentsByRating(Integer rating);
}
