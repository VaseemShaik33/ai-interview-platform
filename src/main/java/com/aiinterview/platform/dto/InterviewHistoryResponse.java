package com.aiinterview.platform.dto;

import java.time.LocalDateTime;

import com.aiinterview.platform.entity.InterviewStatus;

public record InterviewHistoryResponse(
        Long sessionId,
        String category,
        String difficulty,
        int totalQuestions,
        int answeredQuestions,
        Long totalScore,
        Long maximumScore,
        double percentage,
        InterviewStatus status,
        LocalDateTime startedAt,
        LocalDateTime completedAt) {
}