package com.dgh.patientfeedbacksystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @Lob
    private String originalText;

    @Lob
    private String transcribedVoiceText;

    @Enumerated(EnumType.STRING)
    private LanguagePreference language;

    @Lob
    private String summary;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
