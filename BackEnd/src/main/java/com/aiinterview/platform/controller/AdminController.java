package com.aiinterview.platform.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aiinterview.platform.dto.AdminCandidateResponse;
import com.aiinterview.platform.dto.AdminDashboardResponse;
import com.aiinterview.platform.dto.RecentInterviewResponse;
import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.entity.Question;
import com.aiinterview.platform.entity.InterviewAnswer;
import com.aiinterview.platform.entity.InterviewSession;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.repository.InterviewAnswerRepository;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.repository.InterviewSessionRepository;
import com.aiinterview.platform.repository.QuestionRepository;
import com.aiinterview.platform.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final InterviewCategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final InterviewSessionRepository sessionRepository;
    private final InterviewAnswerRepository answerRepository;

    public AdminController(InterviewCategoryRepository categoryRepository, QuestionRepository questionRepository,
                           UserRepository userRepository, InterviewSessionRepository sessionRepository,
                           InterviewAnswerRepository answerRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        List<User> candidates = userRepository.findAll().stream()
                .filter(u -> "CANDIDATE".equalsIgnoreCase(u.getRole())).toList();
        List<InterviewSession> sessions = sessionRepository.findAll();
        double average = sessions.stream().mapToDouble(this::percentage).average().orElse(0);

        List<AdminCandidateResponse> candidateRows = candidates.stream().map(u -> {
            List<InterviewSession> mine = sessionRepository.findByUserId(u.getId());
            double avg = mine.stream().mapToDouble(this::percentage).average().orElse(0);
            boolean active = mine.stream().anyMatch(s -> s.getStatus() != null && s.getStatus().name().equals("IN_PROGRESS"));
            return new AdminCandidateResponse(u.getId(), u.getName(), u.getEmail(), u.getRole(), mine.size(), avg, active ? "Active" : "Inactive");
        }).toList();

        List<RecentInterviewResponse> recent = sessions.stream()
                .sorted((a,b) -> b.getStartedAt().compareTo(a.getStartedAt()))
                .limit(8)
                .map(s -> new RecentInterviewResponse(s.getId(), s.getCategory().getName(), s.getDifficulty().name(), percentage(s), s.getStatus().name(), s.getStartedAt()))
                .toList();

        long activeCandidates = candidateRows.stream().filter(c -> "Active".equals(c.status())).count();
        return new AdminDashboardResponse(candidates.size(), sessions.size(), average, activeCandidates, candidateRows, recent);
    }

    @GetMapping("/candidates")
    public List<AdminCandidateResponse> candidates() { return dashboard().candidates(); }

    @GetMapping("/interviews")
    public List<RecentInterviewResponse> interviews() { return dashboard().recentInterviews(); }

    @PostMapping("/categories")
    public ResponseEntity<InterviewCategory> createCategory(@RequestBody InterviewCategory category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    @GetMapping("/categories")
    public List<InterviewCategory> getCategories() { return categoryRepository.findAll(); }

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionRepository.save(question));
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() { return questionRepository.findAll(); }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (!questionRepository.existsById(id)) return ResponseEntity.notFound().build();
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private double percentage(InterviewSession session) {
        List<InterviewAnswer> answers = answerRepository.findBySessionId(session.getId());
        long score = answers.stream().mapToLong(a -> a.getScore() == null ? 0 : a.getScore()).sum();
        long max = session.getTotalQuestions() * 10L;
        return max == 0 ? 0 : score * 100.0 / max;
    }
}
