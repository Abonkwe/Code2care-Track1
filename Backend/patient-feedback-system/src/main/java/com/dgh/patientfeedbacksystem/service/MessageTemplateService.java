package com.dgh.patientfeedbacksystem.service;

public interface MessageTemplateService {
    String getReminderMessage(String patientName, String date, String lang);
}
