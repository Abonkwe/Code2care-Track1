package com.dgh.patientfeedbacksystem.dto;

import com.dgh.patientfeedbacksystem.entity.LanguagePreference;
import lombok.Data;

@Data
public class FeedbackRequest {
    private Long patientId;
    private String text;
    private String transcribedVoiceText; // Optional if submitted via voice
    private LanguagePreference language;
}
