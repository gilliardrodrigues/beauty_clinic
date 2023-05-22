package com.ubi.beautyClinic.application.ports.out;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;

import java.util.List;

public interface ProfessionalRepositoryOutboundPort {

    Boolean professionalExists(Long id);
    Professional save(Professional professional) throws BusinessLogicException;
    List<Professional> findAll();
    void delete(Long id);
    Professional findById(Long id);
    List<Professional> findByFullNameStartingWith(String name);
}
