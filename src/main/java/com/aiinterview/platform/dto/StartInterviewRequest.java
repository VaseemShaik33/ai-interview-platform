package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.Difficulty;

public record StartInterviewRequest(
     Long categoryId,
        Difficulty difficulty,
        Integer numberOfQuestions
) {
     
}
