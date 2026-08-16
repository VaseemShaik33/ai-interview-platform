package com.aiinterview.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiinterview.platform.dto.InterviewHistoryResponse;
import com.aiinterview.platform.dto.InterviewResultResponse;
import com.aiinterview.platform.dto.StartInterviewRequest;
import com.aiinterview.platform.dto.StartInterviewResponse;
import com.aiinterview.platform.dto.SubmitAnswerRequest;
import com.aiinterview.platform.dto.SubmitAnswerResponse;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.service.InterviewSessionService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/interviews")
public class InterviewSessionController {
    private final InterviewSessionService interviewSessionService;

    public InterviewSessionController(InterviewSessionService interviewSessionService) {
        this.interviewSessionService = interviewSessionService;
    }

    @PostMapping("/start")
    public StartInterviewResponse startInterview(
            @RequestBody StartInterviewRequest request,
            Authentication authentication) {

        System.out.println("CONTROLLER CALLED");

        User user = (User) authentication.getPrincipal();

        return interviewSessionService.startInterview(request, user);
    }

    @PostMapping("/{sessionId}/answer")
    public SubmitAnswerResponse submitAnswer(
            @PathVariable Long sessionId,
            @Valid @RequestBody SubmitAnswerRequest request) {

        return interviewSessionService.submitAnswer(
                sessionId,
                request);
    }

    @GetMapping("/{sessionId}/result")
    public InterviewResultResponse getResult(@PathVariable Long sessionId) {
        return interviewSessionService.getResult(sessionId);
    }

    @GetMapping("/history")
    public List<InterviewHistoryResponse> getInterviewHistory(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return interviewSessionService
                .getInterviewHistory(user.getId());
    }
}
