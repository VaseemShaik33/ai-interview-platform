package com.aiinterview.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiinterview.platform.dto.StartInterviewRequest;
import com.aiinterview.platform.dto.StartInterviewResponse;
import com.aiinterview.platform.dto.SubmitAnswerRequest;
import com.aiinterview.platform.dto.SubmitAnswerResponse;
import com.aiinterview.platform.service.InterviewSessionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/interviews")
public class InterviewSessionController {
    private final InterviewSessionService interviewSessionService;

    public InterviewSessionController(InterviewSessionService interviewSessionService){
        this.interviewSessionService=interviewSessionService;
    }


    @PostMapping("/start")
    public StartInterviewResponse startInterview(@RequestBody StartInterviewRequest request) {

    System.out.println("CONTROLLER CALLED");
        return interviewSessionService.startInterview(request);
    }
    
  @PostMapping("/{sessionId}/answer")
public SubmitAnswerResponse submitAnswer(
        @PathVariable Long sessionId,
        @Valid @RequestBody SubmitAnswerRequest request) {

    return interviewSessionService.submitAnswer(
            sessionId,
            request
    );
}
}
