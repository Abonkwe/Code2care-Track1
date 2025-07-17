package com.dgh.patientfeedbacksystem.dto;

import com.dgh.patientfeedbacksystem.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentRequest {
    private Long patientId;
    private LocalDate appointmentDate;
    private String appointmentPurpose;
    private AppointmentStatus appointmentStatus;
}
