package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.InterviewStatus;

public record InterviewResultResponse(
     Long sessionId,
        String category,
        int totalQuestions,
        int answeredQuestions,
        long totalScore,
        long maximumScore,
        double percentage,
        InterviewStatus status
) {
} 