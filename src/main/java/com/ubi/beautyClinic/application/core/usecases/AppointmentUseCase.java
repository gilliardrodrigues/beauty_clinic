package com.ubi.beautyClinic.application.core.usecases;

import com.ubi.beautyClinic.application.core.domain.Appointment;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.AppointmentUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.AppointmentRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.PatientRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class AppointmentUseCase implements AppointmentUseCaseInboundPort {

    private final AppointmentRepositoryOutboundPort outboundPort;
    private final PatientRepositoryOutboundPort patientRepositoryOutboundPort;
    private final ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort;

    public AppointmentUseCase(AppointmentRepositoryOutboundPort outboundPort, PatientRepositoryOutboundPort patientRepositoryOutboundPort, ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort) {
        this.outboundPort = outboundPort;
        this.patientRepositoryOutboundPort = patientRepositoryOutboundPort;
        this.professionalRepositoryOutboundPort = professionalRepositoryOutboundPort;
    }

    @Override
    public Appointment acceptAppointment(Long id) {

        var appointment = outboundPort.findById(id);
        if(appointment.getStatus().equals(Status.REQUESTED))
            appointment.setStatus(Status.TO_ACCOMPLISH);

        return outboundPort.save(appointment);
    }

    @Override
    public Appointment refuseAppointment(Long id) {

        var appointment = outboundPort.findById(id);
        if(appointment.getStatus().equals(Status.REQUESTED))
            appointment.setStatus(Status.REFUSED);

        return outboundPort.save(appointment);
    }

    public Appointment markAppointmentAsAccomplished(Appointment appointment) {

        if(appointment.getStatus().equals(Status.TO_ACCOMPLISH))
            appointment.setStatus(Status.ACCOMPLISHED);
        return outboundPort.save(appointment);
    }

    @Override
    @Transactional
    public Appointment requestAppointment(Appointment appointment) throws BusinessLogicException {

        var loggedPatientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var loggedPatient = patientRepositoryOutboundPort.findByEmail(loggedPatientEmail);
        var professional = professionalRepositoryOutboundPort.findById(appointment.getProfessional().getId());

        if(!professional.getOfferedServices().contains(appointment.getService()))
            throw new BusinessLogicException("The selected professional does not offer this service!");

        appointment.setProfessional(professional);
        appointment.setPatient(loggedPatient);
        appointment.setStatus(Status.REQUESTED);
        appointment.setRating(null);
        appointment.setDateTime(appointment.getDateTime().withSecond(0).withNano(0));

        return outboundPort.save(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {

        outboundPort.delete(id);
    }

    @Override
    public Appointment getAppointmentById(Long id) {

        return outboundPort.findById(id);
    }

    private void updateAccomplishedAppointments() {

        var currentDateTime = OffsetDateTime.now();
        var appointments = outboundPort.findAll();

        appointments.stream()
                    .filter(appointment -> appointment.getStatus().equals(Status.TO_ACCOMPLISH) && appointment.getDateTime().isBefore(currentDateTime))
                    .forEach(appointment -> {
                        appointment.setStatus(Status.ACCOMPLISHED);
                        outboundPort.save(appointment);
                    });
    }

    @Override
    public List<Appointment> listAppointmentsForLoggedUser() {

        var loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var loggedUserAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        updateAccomplishedAppointments();

        if(loggedUserAuthorities.contains(new SimpleGrantedAuthority("PATIENT"))) {
            var patient = patientRepositoryOutboundPort.findByEmail(loggedUserEmail);
            var appointments = outboundPort.findByPatient(patient);
            return appointments;
        }
        if(loggedUserAuthorities.contains(new SimpleGrantedAuthority("PROFESSIONAL"))) {
            var professional = professionalRepositoryOutboundPort.findByEmail(loggedUserEmail);
            var appointments = outboundPort.findByProfessional(professional);
            return appointments;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Appointment> listAppointmentsByDate(OffsetDateTime date) {

        OffsetDateTime startOfDay = date.with(LocalTime.MIDNIGHT).withOffsetSameInstant(ZoneOffset.ofHours(-3));
        OffsetDateTime endOfDay = date.with(LocalTime.MAX).withOffsetSameInstant(ZoneOffset.ofHours(-3));

        return outboundPort.findByDate(startOfDay, endOfDay);
    }

    @Override
    public List<Appointment> listAppointmentsByDateTime(OffsetDateTime datetime) {

        return outboundPort.findByDateTime(datetime);
    }

    @Override
    public List<Appointment> listAppointmentsByService(ServiceEnum service) {

        return outboundPort.findByService(service);
    }

    @Override
    public List<Appointment> listAppointmentsByStatus(Status status) {

        return outboundPort.findByStatus(status);
    }

    @Override
    public List<Appointment> listAppointmentsByRating(Integer rating) {

        return outboundPort.findByRating(rating);
    }
}
