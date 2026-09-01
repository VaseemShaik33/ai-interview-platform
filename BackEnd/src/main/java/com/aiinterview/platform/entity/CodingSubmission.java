package com.aiinterview.platform.entity;

import com.aiinterview.platform.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coding_submissions")
public class CodingSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private CodingProblem problem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
    private String language;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;

    @Column(nullable = false)
    private int passedTests;

    @Column(nullable = false)
    private int totalTests;

    @Column(columnDefinition = "TEXT")
    private String outputMessage;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    public CodingSubmission() {
    }

    public CodingSubmission(CodingProblem problem, User user, String language, String code,
            SubmissionStatus status, int passedTests, int totalTests,
            String outputMessage) {
        this.problem = problem;
        this.user = user;
        this.language = language;
        this.code = code;
        this.status = status;
        this.passedTests = passedTests;
        this.totalTests = totalTests;
        this.outputMessage = outputMessage;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public CodingProblem getProblem() {
        return problem;
    }

    public User getUser() {
        return user;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}
