package com.dgh.patientfeedbacksystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    private LocalDate appointmentDate;

    private String appointmentPurpose;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    private boolean reminderSent;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
