package com.aiinterview.platform.controller;

import com.aiinterview.platform.dto.*;
import com.aiinterview.platform.service.CodingProblemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coding")
public class CodingController {
    private final CodingProblemService service;

    public CodingController(CodingProblemService service) {
        this.service = service;
    }

    @GetMapping("/problems")
    public List<ProblemSummary> problems() {
        return service.list();
    }

    @GetMapping("/problems/{id}")
    public ProblemDetails problem(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping("/problems/{id}/run")
    public CodeResult run(@PathVariable Long id, @Valid @RequestBody CodeRequest request) {
        return service.run(id, request);
    }

    @PostMapping("/problems/{id}/submit")
    public CodeResult submit(@PathVariable Long id, @Valid @RequestBody CodeRequest request) {
        return service.submit(id, request);
    }

    @GetMapping("/submissions")
    public List<SubmissionResponse> submissions() {
        return service.submissions();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats() {
        return ResponseEntity.ok(java.util.Map.of("solved", service.solvedCount()));
    }
}
