package com.aiinterview.platform.dto;

import jakarta.validation.constraints.NotBlank;

public record SubmitAnswerRequest(
    @NotBlank
    String userAnswer
) {
    
}
