package com.aiinterview.platform.dto;

public record AnswerFeedbackResponse(
        Long questionId,
        String questionText,
        String userAnswer,
        Long score,
        String correctness,
        String feedback
) {}
