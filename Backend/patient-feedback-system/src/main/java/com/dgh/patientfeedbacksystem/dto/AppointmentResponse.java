package com.dgh.patientfeedbacksystem.dto;

import com.dgh.patientfeedbacksystem.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientName;
    private LocalDate appointmentDate;
    private String appointmentPurpose;
    private AppointmentStatus appointmentStatus;
    private boolean reminderSent;
    private LocalDateTime createdAt;
}
