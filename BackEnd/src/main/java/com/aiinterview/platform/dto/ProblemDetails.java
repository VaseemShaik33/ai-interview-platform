package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.CodingDifficulty;

public record ProblemDetails(
                Long id,
                String title,
                CodingDifficulty difficulty,
                String description,
                String constraints,
                String examples,
                String starterCode) {
}
