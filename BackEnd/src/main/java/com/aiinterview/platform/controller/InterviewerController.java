package com.aiinterview.platform.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.aiinterview.platform.entity.InterviewSession;
import com.aiinterview.platform.repository.InterviewSessionRepository;
import com.aiinterview.platform.service.InterviewSessionService;

@RestController
@RequestMapping("/api/interviewer")
public class InterviewerController {

    private final InterviewSessionRepository interviewSessionRepository;
    private final InterviewSessionService interviewSessionService;

    public InterviewerController(
            InterviewSessionRepository interviewSessionRepository,
            InterviewSessionService interviewSessionService) {

        this.interviewSessionRepository = interviewSessionRepository;

        this.interviewSessionService = interviewSessionService;
    }

    // View all interview sessions

    @GetMapping("/interviews")
    public List<InterviewSession> getAllInterviews() {

        return interviewSessionRepository.findAll();
    }

    // View one interview session

    @GetMapping("/interviews/{sessionId}")
    public InterviewSession getInterview(
            @PathVariable Long sessionId) {

        return interviewSessionRepository
                .findById(sessionId)
                .orElseThrow(() -> new RuntimeException(
                        "Interview session not found"));
    }
}