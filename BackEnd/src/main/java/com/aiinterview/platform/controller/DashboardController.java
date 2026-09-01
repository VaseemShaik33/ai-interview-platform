package com.aiinterview.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aiinterview.platform.dto.CandidateDashboardResponse;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.service.DashboardService;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/candidate")
    public CandidateDashboardResponse candidate(Authentication authentication) {
        return dashboardService.candidate((User) authentication.getPrincipal());
    }
}
