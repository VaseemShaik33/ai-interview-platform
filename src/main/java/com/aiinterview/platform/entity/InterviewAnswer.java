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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterviewSession getSession() {
        return session;
    }

    public void setSession(InterviewSession session) {
        this.session = session;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Correctness getCorrectness() {
        return correctness;
    }

    public void setCorrectness(Correctness correctness) {
        this.correctness = correctness;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }

    private String userAnswer;

    private Long score;

    private String feedback;

    @Enumerated(EnumType.STRING)
    private Correctness correctness;

    private LocalDateTime answeredAt;
}
