package com.ubi.beautyClinic.application.core.usecases;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.UserAlreadyExistsException;
import com.ubi.beautyClinic.application.ports.in.ProfessionalUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProfessionalUseCase implements ProfessionalUseCaseInboundPort {

    private final ProfessionalRepositoryOutboundPort outboundPort;
    private final PasswordEncoder bcryptEncoder;

    public ProfessionalUseCase(ProfessionalRepositoryOutboundPort outboundPort, PasswordEncoder bcryptEncoder) {

        this.outboundPort = outboundPort;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public Boolean professionalExists(Long id) {

        return outboundPort.professionalExists(id);
    }

    @Override
    @Transactional
    public Professional registerProfessional(Professional professional) throws BusinessLogicException {

        if (outboundPort.professionalExists(professional.getEmail()))
            throw new UserAlreadyExistsException("Já existe um cadastro de profissional com este e-mail!");
        professional.setPassword(bcryptEncoder.encode(professional.getPassword()));

        return outboundPort.save(professional);
    }

    @Override
    public UserDetails loadProfessionalByEmail(String email) {

        if (!outboundPort.professionalExists(email))
            throw new UsernameNotFoundException("Professional não encontrado com email: " + email);

        return outboundPort.loadUserByUsername(email);
    }

    @Override
    public Professional updateProfessionalData(Professional professional) throws BusinessLogicException {

        if (!outboundPort.professionalExists(professional.getId()))
            throw new UsernameNotFoundException("Professional não encontrado!");

        return outboundPort.save(professional);
    }

    @Override
    public List<Professional> getAllProfessionals() {

        return outboundPort.findAll();
    }

    @Override
    @Transactional
    public void deleteProfessionalById(Long id) {

        outboundPort.delete(id);
    }

    @Override
    public Professional findProfessionalById(Long id) {

        return outboundPort.findById(id);
    }

    @Override
    public List<Professional> findProfessionalByFullNameStartingWith(String name) {

        return outboundPort.findByFullNameStartingWith(name);
    }
}
