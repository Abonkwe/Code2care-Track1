package com.dgh.patientfeedbacksystem.entity;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    

    @Enumerated(EnumType.STRING)
    private LanguagePreference languagePreference;
     
    @Enumerated(EnumType.STRING)
    private Role role;
}
