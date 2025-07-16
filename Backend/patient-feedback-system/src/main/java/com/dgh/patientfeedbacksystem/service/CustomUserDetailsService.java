package com.dgh.patientfeedbacksystem.service;

import com.dgh.patientfeedbacksystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var patient = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        return User.builder()
                .username(patient.getEmail())
                .password(patient.getPassword())
                .roles(patient.getRole().name())
                .build();
    }
}
