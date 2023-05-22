package com.ubi.beautyClinic.application.ports.in;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;

import java.util.List;

public interface ProfessionalUseCaseInboundPort {

    Boolean professionalExists(Long id);
    Professional registerProfessional(Professional professional) throws BusinessLogicException;
    Professional updateProfessionalData(Professional professional) throws BusinessLogicException;
    List<Professional> getAllProfessionals();
    void deleteProfessionalById(Long id);
    Professional findProfessionalById(Long id);
    List<Professional> findProfessionalByFullNameStartingWith(String name);
}
