package com.ubi.beautyClinic.adapters.outbound.JpaRepositories;

import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    List<ProfessionalEntity> findByFullNameStartingWithIgnoreCase(String fullName);
    ProfessionalEntity findByEmail(String email);
}
