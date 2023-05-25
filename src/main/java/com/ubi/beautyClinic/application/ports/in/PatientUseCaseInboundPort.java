package com.ubi.beautyClinic.application.ports.in;

import com.ubi.beautyClinic.application.core.domain.Patient;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PatientUseCaseInboundPort {

    Boolean patientExists(Long id);
    Patient registerPatient(Patient patient) throws BusinessLogicException;
    UserDetails loadPatientByEmail(String email);
    Patient updatePatientData(Patient patient) throws BusinessLogicException;
    List<Patient> getAllPatients();
    void deletePatientById(Long id);
    Patient findPatientById(Long id);
    List<Patient> findPatientByFullNameStartingWith(String name);
}
