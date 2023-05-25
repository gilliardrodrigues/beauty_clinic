package com.ubi.beautyClinic.adapters.outbound.JpaRepositories;

import com.ubi.beautyClinic.adapters.inbound.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    List<PatientEntity> findByFullNameStartingWithIgnoreCase(String fullName);
    PatientEntity findByEmail(String email);
    Boolean existsByEmail(String email);
}
