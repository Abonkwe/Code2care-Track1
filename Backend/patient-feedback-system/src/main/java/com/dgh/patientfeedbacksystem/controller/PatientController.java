package com.dgh.patientfeedbacksystem.controller;

import com.dgh.patientfeedbacksystem.dto.PatientDto;
import com.dgh.patientfeedbacksystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * This controller handles all CRUD operations for managing patients.
 * All endpoints are protected using JWT.
 */
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<PatientDto>> getAll() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> update(@PathVariable Long id, @RequestBody PatientDto dto) {
        return ResponseEntity.ok(patientService.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
