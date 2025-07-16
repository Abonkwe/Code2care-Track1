package com.dgh.patientfeedbacksystem.util;

import com.dgh.patientfeedbacksystem.entity.Sentiment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LlmServiceTest {

    @Autowired
    private LLMService llmService;

    @Test
    void testSummarize() {
        String text = "The patient was very happy with the service and thanked the staff for their care.";
        String language = "ENGLISH";
        String summary = llmService.summarize(text,language);
        assertNotNull(summary);
        System.out.println("Summary: " + summary);
    }

    @Test
    void testDetectSentimentPositive() {
        String text = "The patient was very happy with the service and thanked the staff for their care.";
        Sentiment sentiment = llmService.detectSentiment(text);
        assertNotNull(sentiment);
        System.out.println("Sentiment: " + sentiment);
    }

    @Test
    void testDetectSentimentNegative() {
        String text = "The patient was angry and complained about the poor service.";
        Sentiment sentiment = llmService.detectSentiment(text);
        assertNotNull(sentiment);
        System.out.println("Sentiment: " + sentiment);
    }

    @Test
    void testDetectSentimentNeutral() {
        String text = "The patient visited the hospital.";
        Sentiment sentiment = llmService.detectSentiment(text);
        assertNotNull(sentiment);
        System.out.println("Sentiment: " + sentiment);
    }
}
