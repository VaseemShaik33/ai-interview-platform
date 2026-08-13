package com.aiinterview.platform.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="interview_sessions")
public class InterviewSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private InterviewCategory category;
     
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

     @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    private int totalQuestions;

    private int currentQuestionNumber;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

}
