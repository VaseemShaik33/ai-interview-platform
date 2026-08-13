package com.aiinterview.platform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class InterviewAnswer {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="session_id")
    private InterviewSession session;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    private String userAnswer;

    private Double score;

    private String feedback;

    @Enumerated(EnumType.STRING)
    private Correctness correctness;

    private LocalDateTime answeredAt;
}
