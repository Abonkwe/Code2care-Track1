package com.dgh.patientfeedbacksystem.service;

import com.dgh.patientfeedbacksystem.dto.PatientDto;

import java.util.List;

public interface PatientService {
    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long id);
    PatientDto updatePatient(Long id, PatientDto patientDto);
    void deletePatient(Long id);
}
