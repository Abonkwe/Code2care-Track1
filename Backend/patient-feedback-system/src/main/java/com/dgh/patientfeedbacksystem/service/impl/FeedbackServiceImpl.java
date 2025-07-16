package com.dgh.patientfeedbacksystem.service.impl;

import com.dgh.patientfeedbacksystem.dto.*;
import com.dgh.patientfeedbacksystem.entity.*;
import com.dgh.patientfeedbacksystem.repository.*;
import com.dgh.patientfeedbacksystem.service.FeedbackService;
import com.dgh.patientfeedbacksystem.util.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final PatientRepository patientRepo;
    private final LLMService llmService;

    @Override
    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        Patient patient = patientRepo.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String combinedText = request.getText() != null ? request.getText() : request.getTranscribedVoiceText();
        String language = request.getLanguage() != null ? request.getLanguage().toString() : "ENGLISH";
        String summary;
        try {
            summary = llmService.summarize(combinedText, language);
        } catch (Exception e) {
            // Optionally log the error here
            throw new RuntimeException("Failed to summarize feedback with Gemini: " + e.getMessage(), e);
        }
        Sentiment sentiment = llmService.detectSentiment(combinedText);

        Feedback feedback = Feedback.builder()
                .patient(patient)
                .originalText(request.getText())
                .transcribedVoiceText(request.getTranscribedVoiceText())
                .language(request.getLanguage())
                .summary(summary)
                .sentiment(sentiment)
                .build();

        feedbackRepo.save(feedback);
        return mapToResponse(feedback);
    }

    @Override
    public FeedbackResponse getFeedback(Long id) {
        return feedbackRepo.findById(id).map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    @Override
    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private FeedbackResponse mapToResponse(Feedback f) {
        FeedbackResponse r = new FeedbackResponse();
        r.setId(f.getId());
        r.setPatientId(f.getPatient().getId());
        r.setOriginalText(f.getOriginalText() != null ? f.getOriginalText() : f.getTranscribedVoiceText());
        r.setSummary(f.getSummary());
        r.setSentiment(f.getSentiment());
        r.setCreatedAt(f.getCreatedAt());
        return r;
    }
}
