package com.dgh.patientfeedbacksystem.controller;

import com.dgh.patientfeedbacksystem.dto.FeedbackRequest;
import com.dgh.patientfeedbacksystem.dto.FeedbackResponse;
import com.dgh.patientfeedbacksystem.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponse> submit(@RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.submitFeedback(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedback(id));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getAll() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }
}
