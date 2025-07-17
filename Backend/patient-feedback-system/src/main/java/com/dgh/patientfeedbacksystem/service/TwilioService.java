package com.dgh.patientfeedbacksystem.service;

public interface TwilioService {
    void sendSms(String to, String message);
    void sendVoiceCall(String to, String messageUrl);
}
