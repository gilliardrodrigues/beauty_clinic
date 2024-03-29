package com.ubi.beautyClinic.application.core.usecases;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import com.ubi.beautyClinic.application.core.exceptions.UserAlreadyExistsException;
import com.ubi.beautyClinic.application.ports.in.ProfessionalUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProfessionalUseCase implements ProfessionalUseCaseInboundPort {

    private final ProfessionalRepositoryOutboundPort outboundPort;

    public ProfessionalUseCase(ProfessionalRepositoryOutboundPort outboundPort) {

        this.outboundPort = outboundPort;
    }

    @Override
    public Boolean professionalExists(Long id) {

        return outboundPort.professionalExists(id);
    }

    @Override
    @Transactional
    public Professional registerProfessional(Professional professional) throws BusinessLogicException {

        if (outboundPort.professionalExists(professional.getEmail()))
            throw new UserAlreadyExistsException("A professional registration with this email already exists!");

        return outboundPort.save(professional);
    }

    @Override
    public UserDetails loadProfessionalByEmail(String email) {

        if (!outboundPort.professionalExists(email))
            throw new ObjectNotFoundException("Professional not found with email:" + email);

        return outboundPort.loadUserByUsername(email);
    }

    @Override
    @Transactional
    public Professional updateProfessionalData(Professional professional) throws BusinessLogicException {

        if (!outboundPort.professionalExists(professional.getId()))
            throw new ObjectNotFoundException("Professional not found with ID: " + professional.getId());

        return outboundPort.save(professional);
    }

    @Override
    public List<Professional> getAllProfessionals() {

        return outboundPort.findAll();
    }

    @Override
    @Transactional
    public void deleteProfessionalById(Long id) {

        if(!outboundPort.professionalExists(id))
            throw new ObjectNotFoundException("Professional not found with ID: " + id);

        outboundPort.delete(id);
    }

    @Override
    public Professional findProfessionalById(Long id) {

        if(!outboundPort.professionalExists(id))
            throw new ObjectNotFoundException("Professional not found with ID: " + id);

        return outboundPort.findById(id);
    }

    @Override
    public List<Professional> findProfessionalByFullNameStartingWith(String name) {

        return outboundPort.findByFullNameStartingWith(name);
    }

    @Override
    public List<Professional> findProfessionalsByService(ServiceEnum service) {

        return outboundPort.findByService(service);
    }
}
