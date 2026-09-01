package com.aiinterview.platform.service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import com.aiinterview.platform.dto.CandidateDashboardResponse;
import com.aiinterview.platform.dto.RecentInterviewResponse;
import com.aiinterview.platform.entity.InterviewAnswer;
import com.aiinterview.platform.entity.InterviewSession;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.repository.InterviewAnswerRepository;
import com.aiinterview.platform.repository.InterviewSessionRepository;

@Service
public class DashboardService {
    private final InterviewSessionRepository sessionRepository;
    private final InterviewAnswerRepository answerRepository;

    public DashboardService(InterviewSessionRepository sessionRepository, InterviewAnswerRepository answerRepository) {
        this.sessionRepository = sessionRepository;
        this.answerRepository = answerRepository;
    }

    public CandidateDashboardResponse candidate(User user) {
        List<InterviewSession> sessions = sessionRepository.findByUserIdOrderByStartedAtDesc(user.getId());
        List<InterviewSession> completed = sessions.stream().filter(s -> s.getStatus() != null && s.getStatus().name().equals("COMPLETED")).toList();

        List<Double> scores = completed.stream().map(this::percentage).toList();
        double average = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double best = scores.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        long minutes = sessions.stream().mapToLong(this::durationMinutes).sum();

        List<RecentInterviewResponse> recent = sessions.stream().limit(5).map(s -> new RecentInterviewResponse(
                s.getId(), s.getCategory().getName(), s.getDifficulty().name(), percentage(s), s.getStatus().name(), s.getStartedAt()
        )).toList();

        return new CandidateDashboardResponse(sessions.size(), average, best, minutes, recent);
    }

    private double percentage(InterviewSession session) {
        List<InterviewAnswer> answers = answerRepository.findBySessionId(session.getId());
        long score = answers.stream().mapToLong(a -> a.getScore() == null ? 0 : a.getScore()).sum();
        long max = session.getTotalQuestions() * 10L;
        return max == 0 ? 0 : score * 100.0 / max;
    }

    private long durationMinutes(InterviewSession session) {
        if (session.getStartedAt() == null || session.getCompletedAt() == null) return 0;
        return Math.max(0, Duration.between(session.getStartedAt(), session.getCompletedAt()).toMinutes());
    }
}
