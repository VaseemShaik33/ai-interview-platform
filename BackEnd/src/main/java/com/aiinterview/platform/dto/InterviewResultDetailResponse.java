package com.aiinterview.platform.dto;

import java.util.List;
import com.aiinterview.platform.entity.InterviewStatus;

public record InterviewResultDetailResponse(
        Long sessionId,
        String category,
        String difficulty,
        int totalQuestions,
        int answeredQuestions,
        long totalScore,
        long maximumScore,
        double percentage,
        InterviewStatus status,
        List<AnswerFeedbackResponse> answers,
        List<String> strengths,
        List<String> areasToImprove
) {}
