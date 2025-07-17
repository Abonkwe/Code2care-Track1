package com.dgh.patientfeedbacksystem.scheduler;

import com.dgh.patientfeedbacksystem.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled job that runs every hour and sends appointment reminders.
 */
@Component
@RequiredArgsConstructor
public class AppointmentReminderScheduler {

    private final ReminderService reminderService;

    // Runs every hour (3600000 ms = 1 hour)
    @Scheduled(fixedRate = 3600000)
    public void processReminders() {
        System.out.println("[Scheduler] Checking for appointment reminders...");
        reminderService.sendUpcomingAppointmentReminders();
    }
}
