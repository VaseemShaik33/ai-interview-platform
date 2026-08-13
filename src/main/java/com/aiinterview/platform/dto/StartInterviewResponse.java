package com.aiinterview.platform.dto;

public record StartInterviewResponse(

        Long sessionId,
        Integer questionNumber,
        Integer totalQuestions,
        QuestionResponse question) {

}
