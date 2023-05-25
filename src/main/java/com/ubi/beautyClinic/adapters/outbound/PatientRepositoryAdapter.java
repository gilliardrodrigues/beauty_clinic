package com.ubi.beautyClinic.adapters.outbound;

import com.ubi.beautyClinic.adapters.inbound.entities.PatientEntity;
import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.outbound.JpaRepositories.PatientRepository;
import com.ubi.beautyClinic.application.core.domain.Patient;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.ports.out.PatientRepositoryOutboundPort;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PatientRepositoryAdapter implements PatientRepositoryOutboundPort, UserDetailsService {

    private final PatientRepository repository;
    private final GenericMapper mapper;


    public PatientRepositoryAdapter(PatientRepository repository, GenericMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Boolean patientExists(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Boolean patientExists(String email) {

        return repository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var patientEntity = repository.findByEmail(email);
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(email)
                .password(patientEntity.getPassword())
                .authorities("PATIENT")
                .build();
    }

    @Override
    public Patient save(Patient patient) throws BusinessLogicException {

        var patientEntity = mapper.mapTo(patient, PatientEntity.class);
        return mapper.mapTo(repository.save(patientEntity), Patient.class);
    }

    @Override
    public List<Patient> findAll() {

        var patients = repository.findAll();
        return mapper.mapToList(patients, new TypeToken<List<Patient>>() {}.getType());
    }

    @Override
    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Patient findById(Long id) {

        var patientEntity = repository.findById(id);
        return patientEntity.map(entity -> mapper.mapTo(entity, Patient.class))
                .orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
    }

    @Override
    public List<Patient> findByFullNameStartingWith(String name) {

        var patients = repository.findByFullNameStartingWithIgnoreCase(name);
        return mapper.mapToList(patients, new TypeToken<List<Patient>>() {}.getType());
    }
}
