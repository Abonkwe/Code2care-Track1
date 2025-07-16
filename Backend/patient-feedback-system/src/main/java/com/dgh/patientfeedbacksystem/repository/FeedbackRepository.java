package com.dgh.patientfeedbacksystem.repository;

import com.dgh.patientfeedbacksystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}
