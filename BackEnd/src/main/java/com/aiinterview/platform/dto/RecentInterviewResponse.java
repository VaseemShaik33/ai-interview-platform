package com.aiinterview.platform.dto;

import java.time.LocalDateTime;

public record RecentInterviewResponse(
        Long sessionId,
        String role,
        String difficulty,
        double percentage,
        String status,
        LocalDateTime startedAt
) {}
