package com.aiinterview.platform.dto;

public record AdminCandidateResponse(
        Long userId,
        String name,
        String email,
        String role,
        int interviews,
        double averageScore,
        String status
) {}
