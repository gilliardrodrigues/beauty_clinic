package com.ubi.beautyClinic.application.core.usecases;

import com.ubi.beautyClinic.application.core.domain.Patient;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.core.exceptions.UserAlreadyExistsException;
import com.ubi.beautyClinic.application.ports.in.PatientUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.PatientRepositoryOutboundPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PatientUseCase implements PatientUseCaseInboundPort {

    private final PatientRepositoryOutboundPort outboundPort;

    public PatientUseCase(PatientRepositoryOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    @Override
    public Boolean patientExists(Long id) {

        return outboundPort.patientExists(id);
    }

    @Override
    @Transactional
    public Patient registerPatient(Patient patient) throws BusinessLogicException {

        if (outboundPort.patientExists(patient.getEmail()))
            throw new UserAlreadyExistsException("A patient record with this email already exists!");

        return outboundPort.save(patient);
    }

    @Override
    public UserDetails loadPatientByEmail(String email) {

        if (!outboundPort.patientExists(email))
            throw new ObjectNotFoundException("Patient not found with email: " + email);

        return outboundPort.loadUserByUsername(email);
    }

    @Override
    @Transactional
    public Patient updatePatientData(Patient patient) throws BusinessLogicException {

        if (!outboundPort.patientExists(patient.getId()))
            throw new UsernameNotFoundException("Patient not found with ID: " + patient.getId());

        return outboundPort.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {

        return outboundPort.findAll();
    }

    @Override
    @Transactional
    public void deletePatientById(Long id) {

        if (!outboundPort.patientExists(id))
            throw new UsernameNotFoundException("Patient not found with ID: " + id);

        outboundPort.delete(id);
    }

    @Override
    public Patient findPatientById(Long id) {

        return outboundPort.findById(id);
    }

    @Override
    public List<Patient> findPatientByFullNameStartingWith(String name) {

        return outboundPort.findByFullNameStartingWith(name);
    }
}
