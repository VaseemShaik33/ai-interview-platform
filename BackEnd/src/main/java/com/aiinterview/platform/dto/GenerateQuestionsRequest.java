package com.aiinterview.platform.dto;

public record GenerateQuestionsRequest(
    
    String difficulty,
    Long categoryId,
    Integer numberOfQuestions
) {
    
}
