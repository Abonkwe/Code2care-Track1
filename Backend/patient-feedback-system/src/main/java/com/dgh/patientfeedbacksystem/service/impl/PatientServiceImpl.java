package com.dgh.patientfeedbacksystem.service.impl;

import com.dgh.patientfeedbacksystem.dto.PatientDto;
import com.dgh.patientfeedbacksystem.entity.Patient;
import com.dgh.patientfeedbacksystem.repository.PatientRepository;
import com.dgh.patientfeedbacksystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepo;

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return mapToDto(patient);
    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto dto) {
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        patient.setFullName(dto.getFullName());
        patient.setLanguagePreference(dto.getLanguagePreference());
        patientRepo.save(patient);

        return mapToDto(patient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepo.existsById(id)) {
            throw new RuntimeException("Patient not found");
        }
        patientRepo.deleteById(id);
    }

    private PatientDto mapToDto(Patient p) {
        PatientDto dto = new PatientDto();
        dto.setId(p.getId());
        dto.setFullName(p.getFullName());
        dto.setEmail(p.getEmail());
        dto.setLanguagePreference(p.getLanguagePreference());
        dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }
}
