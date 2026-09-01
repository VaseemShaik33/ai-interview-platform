package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.Difficulty;

public record QuestionResponse(
    Long id,
    String questionText,
    String categoryInformation,
    Difficulty difficulty
) {
    
}
