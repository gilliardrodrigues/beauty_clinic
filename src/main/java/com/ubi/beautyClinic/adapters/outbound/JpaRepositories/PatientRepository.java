package com.ubi.beautyClinic.adapters.outbound.JpaRepositories;

import com.ubi.beautyClinic.adapters.inbound.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}
