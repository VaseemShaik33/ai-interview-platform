package com.aiinterview.platform.dto;

import java.util.List;

public record CandidateDashboardResponse(
        int totalInterviews,
        double averageScore,
        double bestScore,
        long totalMinutes,
        List<RecentInterviewResponse> recentInterviews
) {}
