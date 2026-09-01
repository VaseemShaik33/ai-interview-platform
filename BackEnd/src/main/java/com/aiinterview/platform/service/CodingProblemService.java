package com.aiinterview.platform.service;

import com.aiinterview.platform.dto.*;
import com.aiinterview.platform.entity.*;
import com.aiinterview.platform.repository.CodingProblemRepository;
import com.aiinterview.platform.repository.CodingSubmissionRepository;
import com.aiinterview.platform.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodingProblemService {
    private final CodingProblemRepository problemRepository;
    private final CodingSubmissionRepository submissionRepository;
    private final CurrentUserService currentUserService;
    private final JavaCodeRunner javaCodeRunner;

    public CodingProblemService(CodingProblemRepository problemRepository,
            CodingSubmissionRepository submissionRepository,
            CurrentUserService currentUserService,
            JavaCodeRunner javaCodeRunner) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        this.currentUserService = currentUserService;
        this.javaCodeRunner = javaCodeRunner;
    }

    public List<ProblemSummary> list() {
        return problemRepository.findAllByOrderByIdAsc().stream()
                .map(p -> new ProblemSummary(p.getId(), p.getTitle(), p.getDifficulty()))
                .toList();
    }

    public ProblemDetails get(Long id) {
        CodingProblem p = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found"));
        return new ProblemDetails(p.getId(), p.getTitle(), p.getDifficulty(),
                p.getDescription(), p.getConstraintsText(), p.getExamplesText(), p.getStarterCode());
    }

    public CodeResult run(Long id, CodeRequest request) {
        CodingProblem p = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found"));
        validateJava(request.language());
        return javaCodeRunner.run(p, request.code(), false);
    }

    public CodeResult submit(Long id, CodeRequest request) {
        CodingProblem p = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found"));
        validateJava(request.language());

        CodeResult result = javaCodeRunner.run(p, request.code(), true);
        User user = currentUserService.getRequiredUser();

        submissionRepository.save(new CodingSubmission(
                p, user, request.language(), request.code(),
                SubmissionStatus.valueOf(result.status()),
                result.passedTests(), result.totalTests(), result.message()));

        return result;
    }

    public List<SubmissionResponse> submissions() {
        User user = currentUserService.getRequiredUser();
        return submissionRepository.findTop20ByUserOrderBySubmittedAtDesc(user)
                .stream()
                .map(s -> new SubmissionResponse(
                        s.getId(), s.getProblem().getId(), s.getProblem().getTitle(),
                        s.getLanguage(), s.getStatus().name(), s.getPassedTests(),
                        s.getTotalTests(), s.getSubmittedAt()))
                .toList();
    }

    public long solvedCount() {
        User user = currentUserService.getRequiredUser();
        return submissionRepository.countByUserAndStatus(user, SubmissionStatus.ACCEPTED);
    }

    private void validateJava(String language) {
        if (!"java".equalsIgnoreCase(language)) {
            throw new IllegalArgumentException("MVP currently supports Java only");
        }
    }
}
