package com.dgh.patientfeedbacksystem.dto;

import com.dgh.patientfeedbacksystem.entity.Sentiment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackResponse {
    private Long id;
    private Long patientId;
    private String originalText;
    private String summary;
    private Sentiment sentiment;
    private LocalDateTime createdAt;
}
