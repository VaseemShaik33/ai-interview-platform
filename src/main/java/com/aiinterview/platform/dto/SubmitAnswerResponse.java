package com.aiinterview.platform.dto;

public record SubmitAnswerResponse(
        Long sessionId,
        Integer questionNumber,
        Integer totalQuestions,
        QuestionResponse question
) {
}