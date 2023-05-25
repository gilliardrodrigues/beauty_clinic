package com.ubi.beautyClinic.adapters.outbound;

import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.outbound.JpaRepositories.ProfessionalRepository;
import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessionalRepositoryAdapter implements ProfessionalRepositoryOutboundPort, UserDetailsService {

    private final ProfessionalRepository repository;

    private final PasswordEncoder bcryptEncoder;

    private final GenericMapper mapper;

    public ProfessionalRepositoryAdapter(ProfessionalRepository repository, PasswordEncoder bcryptEncoder, GenericMapper mapper) {
        this.repository = repository;
        this.bcryptEncoder = bcryptEncoder;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var professional = repository.findByEmail(email);
        if (professional == null) {
            throw new UsernameNotFoundException("Professional not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(professional.getEmail(), professional.getPassword(),
                new ArrayList<>());
    }

    @Override
    public Boolean professionalExists(Long id) {

        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Professional save(Professional professional) throws BusinessLogicException {

        var professionalEntity =mapper.mapTo(professional, ProfessionalEntity.class);
        professionalEntity.setPassword(bcryptEncoder.encode(professionalEntity.getPassword()));
        return mapper.mapTo(repository.save(professionalEntity), Professional.class);
    }

    @Override
    public List<Professional> findAll() {

        var professionals = repository.findAll();
        return mapper.mapToList(professionals, new TypeToken<List<Professional>>() {}.getType());
    }

    @Override
    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Professional findById(Long id) {

        var professionalEntity = repository.findById(id);
        return professionalEntity.map(entity -> mapper.mapTo(entity, Professional.class))
                .orElseThrow(() -> new ObjectNotFoundException("Profissional não encontrado!"));
    }

    @Override
    public List<Professional> findByFullNameStartingWith(String name) {

        var professionals = repository.findByFullNameStartingWithIgnoreCase(name);
        return mapper.mapToList(professionals, new TypeToken<List<Professional>>() {}.getType());
    }
}
