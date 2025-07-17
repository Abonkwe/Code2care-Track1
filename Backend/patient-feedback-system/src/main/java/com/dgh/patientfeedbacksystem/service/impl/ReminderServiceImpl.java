package com.dgh.patientfeedbacksystem.service.impl;

import com.dgh.patientfeedbacksystem.entity.Appointment;
import com.dgh.patientfeedbacksystem.entity.AppointmentStatus;
import com.dgh.patientfeedbacksystem.entity.ReminderPreference;
import com.dgh.patientfeedbacksystem.repository.AppointmentRepository;
import com.dgh.patientfeedbacksystem.service.ReminderService;
import com.dgh.patientfeedbacksystem.service.MessageTemplateService;
import com.dgh.patientfeedbacksystem.service.TwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final AppointmentRepository appointmentRepo;
    private final TwilioService twilioService;
    private final MessageTemplateService messageTemplateService;

    @Override
    public void sendUpcomingAppointmentReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Appointment> upcomingAppointments = appointmentRepo.findAll().stream()
                .filter(a -> a.getAppointmentStatus() == AppointmentStatus.SCHEDULED
                        && !a.isReminderSent()
                        && a.getAppointmentDate().isEqual(tomorrow))
                .toList();

        for (Appointment a : upcomingAppointments) {
         try {
         String lang = a.getPatient().getLanguagePreference().name();
          String message = messageTemplateService.getReminderMessage(
                  a.getPatient().getFullName(),
                  a.getAppointmentDate().toString(),
                  lang
             );

if (a.getPatient().getReminderPreference() == ReminderPreference.SMS) {
    twilioService.sendSms(a.getPatient().getPhoneNumber(), message);
} else {
    // Create a TwiML XML online or host your own
    String voiceUrl = "https://handler.twilio.com/twiml/EHXXXXXXXXXXXXXXXXXXXXXX";
    twilioService.sendVoiceCall(a.getPatient().getPhoneNumber(), voiceUrl);
}

            } catch (Exception e) {
                System.err.println("Error generating message for appointment ID " + a.getId() + ": " + e.getMessage());
                continue; // Skip this appointment if message generation fails
            }


            // Mark reminder as sent
            a.setReminderSent(true);
            appointmentRepo.save(a);
        }
    }
}
