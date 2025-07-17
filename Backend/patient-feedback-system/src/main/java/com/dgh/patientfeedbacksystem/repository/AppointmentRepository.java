package com.dgh.patientfeedbacksystem.repository;

import com.dgh.patientfeedbacksystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
