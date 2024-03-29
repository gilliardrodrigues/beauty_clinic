package com.ubi.beautyClinic.adapters.outbound.JpaRepositories;

import com.ubi.beautyClinic.adapters.inbound.entities.ProfessionalEntity;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    List<ProfessionalEntity> findByFullNameStartingWithIgnoreCase(String fullName);
    ProfessionalEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT p FROM ProfessionalEntity p WHERE :service IN elements(p.offeredServices)")
    List<ProfessionalEntity> findByService(@Param("service") ServiceEnum service);
}
