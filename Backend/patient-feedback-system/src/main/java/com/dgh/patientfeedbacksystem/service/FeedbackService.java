package com.dgh.patientfeedbacksystem.service;

import com.dgh.patientfeedbacksystem.dto.FeedbackRequest;
import com.dgh.patientfeedbacksystem.dto.FeedbackResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse submitFeedback(FeedbackRequest request);
    FeedbackResponse getFeedback(Long id);
    List<FeedbackResponse> getAllFeedback();
}
