package com.dgh.patientfeedbacksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PatientFeedbackSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientFeedbackSystemApplication.class, args);
	}

}
