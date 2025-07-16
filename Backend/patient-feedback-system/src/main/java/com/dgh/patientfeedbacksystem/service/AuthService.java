package com.dgh.patientfeedbacksystem.service;

import com.dgh.patientfeedbacksystem.dto.AuthResponse;
import com.dgh.patientfeedbacksystem.dto.LoginRequest;
import com.dgh.patientfeedbacksystem.dto.RegisterRequest;
import com.dgh.patientfeedbacksystem.entity.Patient;
import com.dgh.patientfeedbacksystem.entity.Role;
import com.dgh.patientfeedbacksystem.repository.PatientRepository;
import com.dgh.patientfeedbacksystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (patientRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Patient patient = Patient.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .languagePreference(request.getLanguagePreference())
                .role(Role.PATIENT)
                .build();

        patientRepo.save(patient);

        String token = jwtUtil.generateToken(patient.getEmail());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Patient patient = patientRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(patient.getEmail());
        return new AuthResponse(token);
    }
}
