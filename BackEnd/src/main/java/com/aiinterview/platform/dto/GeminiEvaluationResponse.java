package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.Correctness;

public record GeminiEvaluationResponse(
        Long score,
        Correctness correctness,
        String feedback
    ) {

}
