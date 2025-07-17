package com.dgh.patientfeedbacksystem.service;

import com.dgh.patientfeedbacksystem.dto.AppointmentRequest;
import com.dgh.patientfeedbacksystem.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse create(AppointmentRequest request);
    List<AppointmentResponse> getAll();
    AppointmentResponse getById(Long id);
    AppointmentResponse update(Long id, AppointmentRequest request);
    void delete(Long id);
}
