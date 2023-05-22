package com.ubi.beautyClinic.application.core.usecases;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.ProfessionalUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;

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
    public Professional registerProfessional(Professional professional) throws BusinessLogicException {

        return outboundPort.save(professional);
    }

    @Override
    public Professional updateProfessionalData(Professional professional) throws BusinessLogicException {

        return outboundPort.save(professional);
    }

    @Override
    public List<Professional> getAllProfessionals() {

        return outboundPort.findAll();
    }

    @Override
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
