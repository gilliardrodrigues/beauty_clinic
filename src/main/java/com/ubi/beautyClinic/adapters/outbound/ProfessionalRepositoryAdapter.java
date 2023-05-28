package com.ubi.beautyClinic.adapters.outbound;

import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.outbound.JpaRepositories.ProfessionalRepository;
import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessionalRepositoryAdapter implements ProfessionalRepositoryOutboundPort, UserDetailsService {

    private final ProfessionalRepository repository;
    private final GenericMapper mapper;
    private final PasswordEncoder bcryptEncoder;

    public ProfessionalRepositoryAdapter(ProfessionalRepository repository, GenericMapper mapper, PasswordEncoder bcryptEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var professionalEntity = repository.findByEmail(email);

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(email)
                .password(professionalEntity.getPassword())
                .authorities("PROFESSIONAL")
                .build();
    }

    @Override
    public Boolean professionalExists(Long id) {

        return repository.existsById(id);
    }

    @Override
    public Boolean professionalExists(String email) {

        return repository.existsByEmail(email);
    }

    @Override
    public Professional save(Professional professional) throws BusinessLogicException {

        professional.setPassword(bcryptEncoder.encode(professional.getPassword()));
        var professionalEntity =mapper.mapTo(professional, ProfessionalEntity.class);
        return mapper.mapTo(repository.save(professionalEntity), Professional.class);
    }

    @Override
    public List<Professional> findAll() {

        var professionals = repository.findAll();
        return mapper.mapToList(professionals, new TypeToken<List<Professional>>() {}.getType());
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Professional findById(Long id) {

        var professionalEntity = repository.findById(id);
        return professionalEntity.map(entity -> mapper.mapTo(entity, Professional.class))
                .orElseThrow(() -> new ObjectNotFoundException("Professional not found with ID: " + id));
    }

    @Override
    public Professional findByEmail(String email) {

        var professionalEntity = repository.findByEmail(email);
        return mapper.mapTo(professionalEntity, Professional.class);
    }

    @Override
    public List<Professional> findByFullNameStartingWith(String name) {

        var professionals = repository.findByFullNameStartingWithIgnoreCase(name);
        return mapper.mapToList(professionals, new TypeToken<List<Professional>>() {}.getType());
    }

    @Override
    public List<Professional> findByService(ServiceEnum service) {

        var professionals = repository.findByService(service);
        return mapper.mapToList(professionals, new TypeToken<List<Professional>>() {}.getType());
    }
}
