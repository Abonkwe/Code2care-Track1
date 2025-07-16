package com.dgh.patientfeedbacksystem.dto;

import com.dgh.patientfeedbacksystem.entity.LanguagePreference;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientDto {
    private Long id;
    private String fullName;
    private String email;
    private LanguagePreference languagePreference;
    private LocalDateTime createdAt;
}
