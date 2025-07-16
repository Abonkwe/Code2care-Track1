package com.dgh.patientfeedbacksystem.repository;

import com.dgh.patientfeedbacksystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}
