package com.ubi.beautyClinic.application.ports.out;

import com.ubi.beautyClinic.application.core.domain.Patient;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface PatientRepositoryOutboundPort {

    Boolean patientExists(Long id);
    Boolean patientExists(String email);
    Patient save(Patient patient) throws BusinessLogicException;
    List<Patient> findAll();
    void delete(Long id);
    Patient findById(Long id);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    List<Patient> findByFullNameStartingWith(String name);
}
