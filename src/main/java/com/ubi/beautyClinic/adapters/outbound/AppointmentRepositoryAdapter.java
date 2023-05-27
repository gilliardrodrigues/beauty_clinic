package com.ubi.beautyClinic.adapters.outbound;

import com.ubi.beautyClinic.adapters.inbound.entities.AppointmentEntity;
import com.ubi.beautyClinic.adapters.inbound.entities.PatientEntity;
import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.outbound.JpaRepositories.AppointmentRepository;
import com.ubi.beautyClinic.application.core.domain.*;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.ports.out.AppointmentRepositoryOutboundPort;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class AppointmentRepositoryAdapter implements AppointmentRepositoryOutboundPort {

    private final AppointmentRepository repository;
    private final GenericMapper mapper;

    public AppointmentRepositoryAdapter(AppointmentRepository repository, GenericMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Appointment save(Appointment appointment) throws BusinessLogicException {

        var appointmentEntity = mapper.mapTo(appointment, AppointmentEntity.class);
        return mapper.mapTo(repository.save(appointmentEntity), Appointment.class);
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public List<Appointment> findAll() {

        var appointments = repository.findAll();
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public Appointment findById(Long id) {

        var appointmentEntity = repository.findById(id);
        return appointmentEntity.map(entity -> mapper.mapTo(entity, Appointment.class))
                .orElseThrow(() -> new ObjectNotFoundException("Appointment not found!"));
    }

    @Override
    public List<Appointment> findByPatient(Patient patient) {

        var patientEntity = mapper.mapTo(patient, PatientEntity.class);
        var appointments = repository.findAllByPatient(patientEntity);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByProfessional(Professional professional) {

        var professionalEntity = mapper.mapTo(professional, ProfessionalEntity.class);
        var appointments = repository.findAllByProfessional(professionalEntity);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByDate(OffsetDateTime startOfDay, OffsetDateTime endOfDay) {

        var appointments = repository.findByDateTimeBetween(startOfDay, endOfDay);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByDateTime(OffsetDateTime datetime) {

        var appointments = repository.findAllByDateTime(datetime);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByService(ServiceEnum service) {

        var appointments = repository.findAllByService(service);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByStatus(Status status) {
        var appointments = repository.findAllByStatus(status);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }

    @Override
    public List<Appointment> findByRating(Integer rating) {

        var appointments = repository.findAllByRating(rating);
        return mapper.mapToList(appointments, new TypeToken<List<Appointment>>() {}.getType());
    }
}
