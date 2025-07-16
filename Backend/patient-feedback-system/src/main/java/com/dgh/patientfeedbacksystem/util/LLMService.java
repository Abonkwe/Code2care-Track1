package com.dgh.patientfeedbacksystem.util;

import com.dgh.patientfeedbacksystem.entity.Sentiment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

/**
 * LLMService using Gemini API for summarization and sentiment detection.
 */
@Component
public class LLMService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;


    private final RestTemplate restTemplate;
    private String discoveredModel = null;

    //@Autowired
    public LLMService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String summarize(String text, String language) {
        String model = getModelToUse();
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + geminiApiKey;
        String prompt = "Summarize this text in the same language as the input (" + language + "): " + text;
        JSONObject requestBody = new JSONObject();
        requestBody.put("contents", new org.json.JSONArray()
            .put(new JSONObject().put("parts", new org.json.JSONArray()
                .put(new JSONObject().put("text", prompt)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject resp = new JSONObject(response.getBody());
                String summary = resp.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
                return summary;
            }
        } catch (Exception e) {
            return "[Gemini API Error: " + e.getMessage() + "]";
        }
        return "[Gemini API Error: No response]";
    }

    public Sentiment detectSentiment(String text) {
        String model = getModelToUse();
        String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + geminiApiKey;
        JSONObject requestBody = new JSONObject();
        requestBody.put("contents", new org.json.JSONArray()
            .put(new JSONObject().put("parts", new org.json.JSONArray()
                .put(new JSONObject().put("text", "Classify the sentiment of this text as POSITIVE, NEGATIVE, or NEUTRAL: " + text)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject resp = new JSONObject(response.getBody());
                String sentimentText = resp.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                    .toUpperCase();
                if (sentimentText.contains("POSITIVE")) return Sentiment.POSITIVE;
                if (sentimentText.contains("NEGATIVE")) return Sentiment.NEGATIVE;
                return Sentiment.NEUTRAL;
            }
        } catch (Exception e) {
            // fallback to NEUTRAL on error
        }
        return Sentiment.NEUTRAL;
    }

    /**
     * Calls Gemini ListModels endpoint to get available models.
     * @return JSON string of available models or error message
     */
    public String listAvailableModels() {
        String url = "https://generativelanguage.googleapis.com/v1beta/models?key=" + geminiApiKey;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject resp = new JSONObject(response.getBody());
                if (resp.has("models")) {
                    // Pick the first model as default, or you can add logic to pick a preferred one
                    String modelName = resp.getJSONArray("models").getJSONObject(0).getString("name");
                    this.discoveredModel = modelName.replace("models/", "");
                }
                return response.getBody();
            } else {
                return "[Gemini API Error: " + response.getStatusCode() + "]";
            }
        } catch (Exception e) {
            return "[Gemini API Error: " + e.getMessage() + "]";
        }
    }

    /**
     * Returns the model to use for API calls. If not discovered, defaults to gemini-2.5-flash.
     */
    private String getModelToUse() {
        if (discoveredModel != null && !discoveredModel.isEmpty()) {
            return discoveredModel;
        }
        return "gemini-2.5-flash";
    }
}
