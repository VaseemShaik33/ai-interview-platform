package com.aiinterview.platform.entity;

import jakarta.persistence.*;

@Entity
public class InterviewSessionQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public int getQuestionOrder() {
        return questionOrder;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    @ManyToOne
    @JoinColumn(name = "session_id")
    private InterviewSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private int questionOrder;
    
}
