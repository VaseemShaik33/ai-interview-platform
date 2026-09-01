package com.aiinterview.platform.dto;

import java.time.LocalDateTime;

public record SubmissionResponse(
                Long id,
                Long problemId,
                String problemTitle,
                String language,
                String status,
                int passedTests,
                int totalTests,
                LocalDateTime submittedAt) {
}
