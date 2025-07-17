package com.dgh.patientfeedbacksystem.service.impl;

import com.dgh.patientfeedbacksystem.dto.AppointmentRequest;
import com.dgh.patientfeedbacksystem.dto.AppointmentResponse;
import com.dgh.patientfeedbacksystem.entity.Appointment;
import com.dgh.patientfeedbacksystem.entity.Patient;
import com.dgh.patientfeedbacksystem.repository.AppointmentRepository;
import com.dgh.patientfeedbacksystem.repository.PatientRepository;
import com.dgh.patientfeedbacksystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;

    @Override
    public AppointmentResponse create(AppointmentRequest request) {
        Patient patient = patientRepo.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .appointmentDate(request.getAppointmentDate())
                .appointmentPurpose(request.getAppointmentPurpose())
                .appointmentStatus(request.getAppointmentStatus())
                .reminderSent(false)
                .build();

        appointmentRepo.save(appointment);
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> getAll() {
        return appointmentRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse getById(Long id) {
        return appointmentRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appt = appointmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appt.setAppointmentDate(request.getAppointmentDate());
        appt.setAppointmentPurpose(request.getAppointmentPurpose());
        appt.setAppointmentStatus(request.getAppointmentStatus());

        appointmentRepo.save(appt);
        return mapToResponse(appt);
    }

    @Override
    public void delete(Long id) {
        appointmentRepo.deleteById(id);
    }

    private AppointmentResponse mapToResponse(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setId(a.getId());
        r.setPatientId(a.getPatient().getId());
        r.setPatientName(a.getPatient().getFullName());
        r.setAppointmentDate(a.getAppointmentDate());
        r.setAppointmentPurpose(a.getAppointmentPurpose());
        r.setAppointmentStatus(a.getAppointmentStatus());
        r.setReminderSent(a.isReminderSent());
        r.setCreatedAt(a.getCreatedAt());
        return r;
    }
}
