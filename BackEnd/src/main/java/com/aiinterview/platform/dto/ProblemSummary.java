package com.aiinterview.platform.dto;

import com.aiinterview.platform.entity.CodingDifficulty;

public record ProblemSummary(Long id, String title, CodingDifficulty difficulty) {
}
