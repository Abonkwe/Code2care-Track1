package com.dgh.patientfeedbacksystem.service.impl;
import com.dgh.patientfeedbacksystem.service.MessageTemplateService;
import org.springframework.stereotype.Service;

@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Override
    public String getReminderMessage(String name, String date, String lang) {
        return switch (lang.toUpperCase()) {
            case "FRENCH" -> "Bonjour " + name + ", vous avez un rendez-vous le " + date;
            case "PIDGIN" -> "Hello " + name + ", you get appointment for " + date;
            case "HAUSA"  -> "Sannu " + name + ", kana da alƙawari a ranar " + date;
            default       -> "Hi " + name + ", you have an appointment on " + date;
        };
    }
}
