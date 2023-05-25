package com.ubi.beautyClinic.application.ports.out;

import com.ubi.beautyClinic.application.core.domain.Professional;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface ProfessionalRepositoryOutboundPort {

    Boolean professionalExists(Long id);
    Professional save(Professional professional) throws BusinessLogicException;
    List<Professional> findAll();
    void delete(Long id);
    Professional findById(Long id);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    List<Professional> findByFullNameStartingWith(String name);
}
