package com.aiinterview.platform.dto;

import java.util.List;

public record AdminDashboardResponse(
        long totalCandidates,
        long totalInterviews,
        double averageScore,
        long activeCandidates,
        List<AdminCandidateResponse> candidates,
        List<RecentInterviewResponse> recentInterviews
) {}
