package com.dgh.patientfeedbacksystem.dto;



import com.dgh.patientfeedbacksystem.entity.LanguagePreference;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private LanguagePreference languagePreference;
}
