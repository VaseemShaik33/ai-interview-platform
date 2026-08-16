package com.aiinterview.platform.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiinterview.platform.dto.GeminiEvaluationResponse;
import com.aiinterview.platform.dto.InterviewHistoryResponse;
import com.aiinterview.platform.dto.InterviewResultResponse;
import com.aiinterview.platform.dto.QuestionResponse;
import com.aiinterview.platform.dto.StartInterviewRequest;
import com.aiinterview.platform.dto.StartInterviewResponse;
import com.aiinterview.platform.dto.SubmitAnswerRequest;
import com.aiinterview.platform.dto.SubmitAnswerResponse;
import com.aiinterview.platform.entity.Correctness;
import com.aiinterview.platform.entity.InterviewAnswer;
import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.entity.InterviewSession;
import com.aiinterview.platform.entity.InterviewSessionQuestion;
import com.aiinterview.platform.entity.InterviewStatus;
import com.aiinterview.platform.entity.Question;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.repository.InterviewAnswerRepository;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.repository.InterviewSessionQuestionRepository;
import com.aiinterview.platform.repository.InterviewSessionRepository;
import com.aiinterview.platform.repository.QuestionRepository;

@Service
public class InterviewSessionService {
        private final InterviewCategoryRepository interviewCategoryRepository;

        private final QuestionRepository questionRepository;

        private final GeminiEvaluationService geminiEvaluationService;

        private final InterviewSessionRepository interviewSessionRepository;

        private final InterviewSessionQuestionRepository interviewSessionQuestionRepository;

        private final InterviewAnswerRepository interviewAnswerRepository;

        public InterviewSessionService(InterviewCategoryRepository interviewCategoryRepositor,
                        QuestionRepository questionRepository, InterviewSessionRepository interviewSessionRepository,
                        InterviewSessionQuestionRepository interviewSessionQuestionRepository,
                        InterviewAnswerRepository interviewAnswerRepository,
                        GeminiEvaluationService geminiEvaluationService) {
                this.interviewCategoryRepository = interviewCategoryRepositor;
                this.questionRepository = questionRepository;
                this.interviewSessionRepository = interviewSessionRepository;
                this.interviewSessionQuestionRepository = interviewSessionQuestionRepository;
                this.interviewAnswerRepository = interviewAnswerRepository;
                this.geminiEvaluationService = geminiEvaluationService;
        }

        public StartInterviewResponse startInterview(StartInterviewRequest request, User user) {
                // 1. Validate number of questions
                if (request.numberOfQuestions() != 5 &&
                                request.numberOfQuestions() != 10 &&
                                request.numberOfQuestions() != 15 &&
                                request.numberOfQuestions() != 20) {

                        throw new RuntimeException("Invalid Number of Questions");
                }

                // 2. Find category
                InterviewCategory category = interviewCategoryRepository.findById(request.categoryId())
                                .orElseThrow(() -> new RuntimeException("Invalid category"));

                // 3. Find questions matching category + difficulty
                List<Question> questions = questionRepository.findByCategoryAndDifficulty(
                                category,
                                request.difficulty());

                // 4. Check enough questions
                if (questions.size() < request.numberOfQuestions()) {
                        throw new RuntimeException("Not Enough Questions");
                }

                // 5. Shuffle questions
                Collections.shuffle(questions);

                // 6. Take required number of questions
                List<Question> selectedQuestions = questions.subList(0, request.numberOfQuestions());

                // 7. Create InterviewSession
                InterviewSession session = new InterviewSession();

                session.setUser(user);
                session.setCategory(category);
                session.setDifficulty(request.difficulty());
                session.setTotalQuestions(request.numberOfQuestions());
                session.setCurrentQuestionNumber(1);
                session.setStatus(InterviewStatus.IN_PROGRESS);
                session.setStartedAt(java.time.LocalDateTime.now());

                // Save session
                session = interviewSessionRepository.save(session);

                // 8. Store selected questions in session
                int order = 1;

                for (Question question : selectedQuestions) {

                        InterviewSessionQuestion sessionQuestion = new InterviewSessionQuestion();

                        sessionQuestion.setSession(session);
                        sessionQuestion.setQuestion(question);
                        sessionQuestion.setQuestionOrder(order);

                        interviewSessionQuestionRepository.save(sessionQuestion);

                        order++;
                }

                // 9. First question
                Question firstQuestion = selectedQuestions.get(0);

                QuestionResponse questionResponse = new QuestionResponse(
                                firstQuestion.getId(),
                                firstQuestion.getQuestionText(),
                                firstQuestion.getCategory().getName(),
                                firstQuestion.getDifficulty());

                // 10. Return Question #1
                return new StartInterviewResponse(
                                session.getId(),
                                1,
                                request.numberOfQuestions(),
                                questionResponse);
        }

        public SubmitAnswerResponse submitAnswer(
                        Long sessionId,
                        SubmitAnswerRequest request) {

                // 1. Find interview session
                InterviewSession session = interviewSessionRepository.findById(sessionId)
                                .orElseThrow(() -> new RuntimeException("Interview session not found"));

                // 2. Get current question number
                int currentQuestionNumber = session.getCurrentQuestionNumber();

                // 3. Find current question
                InterviewSessionQuestion sessionQuestion = interviewSessionQuestionRepository
                                .findBySessionAndQuestionOrder(
                                                session,
                                                currentQuestionNumber)
                                .orElseThrow(() -> new RuntimeException("Question not found"));

                // 4. Create InterviewAnswer
                InterviewAnswer answer = new InterviewAnswer();

                answer.setSession(session);
                answer.setQuestion(sessionQuestion.getQuestion());
                answer.setUserAnswer(request.userAnswer());
                answer.setAnsweredAt(LocalDateTime.now());
                // Temporary values until Gemini is connected
                GeminiEvaluationResponse evaluation = geminiEvaluationService.evaluateAnswer(
                                sessionQuestion.getQuestion().getQuestionText(),
                                request.userAnswer());

                answer.setScore(evaluation.score());
                answer.setFeedback(evaluation.feedback());
                answer.setCorrectness(evaluation.correctness());

                // 5. Save answer
                interviewAnswerRepository.save(answer);

                // 6. Check whether this was the last question
                if (currentQuestionNumber == session.getTotalQuestions()) {

                        session.setStatus(InterviewStatus.COMPLETED);
                        session.setCompletedAt(LocalDateTime.now());
                        interviewSessionRepository.save(session);

                        return new SubmitAnswerResponse(
                                        session.getId(),
                                        currentQuestionNumber,
                                        session.getTotalQuestions(),
                                        null);
                }

                // 7. Move to next question
                int nextQuestionNumber = currentQuestionNumber + 1;

                session.setCurrentQuestionNumber(nextQuestionNumber);

                interviewSessionRepository.save(session);

                // 8. Find next question
                InterviewSessionQuestion nextSessionQuestion = interviewSessionQuestionRepository
                                .findBySessionAndQuestionOrder(
                                                session,
                                                nextQuestionNumber)
                                .orElseThrow(() -> new RuntimeException("Next question not found"));

                Question nextQuestion = nextSessionQuestion.getQuestion();

                // 9. Convert to QuestionResponse
                QuestionResponse questionResponse = new QuestionResponse(
                                nextQuestion.getId(),
                                nextQuestion.getQuestionText(),
                                nextQuestion.getCategory().getName(),
                                nextQuestion.getDifficulty());

                // 10. Return next question
                return new SubmitAnswerResponse(
                                session.getId(),
                                nextQuestionNumber,
                                session.getTotalQuestions(),
                                questionResponse);
        }

        public InterviewResultResponse getResult(Long sessionId) {

                InterviewSession session = interviewSessionRepository
                                .findById(sessionId)
                                .orElseThrow(() -> new RuntimeException("Interview session not found"));

                List<InterviewAnswer> answers = interviewAnswerRepository.findBySessionId(sessionId);

                long totalScore = answers.stream()
                                .mapToLong(InterviewAnswer::getScore)
                                .sum();

                int answeredQuestions = answers.size();

                long maximumScore = session.getTotalQuestions() * 10L;

                double percentage = maximumScore == 0
                                ? 0
                                : (totalScore * 100.0) / maximumScore;

                return new InterviewResultResponse(
                                session.getId(),
                                session.getCategory().getName(),
                                session.getTotalQuestions(),
                                answeredQuestions,
                                totalScore,
                                maximumScore,
                                percentage,
                                session.getStatus());
        }

        public List<InterviewHistoryResponse> getInterviewHistory(Long userId) {

                List<InterviewSession> sessions = interviewSessionRepository
                                .findByUserIdOrderByStartedAtDesc(userId);

                return sessions.stream()
                                .map(session -> {

                                        List<InterviewAnswer> answers = interviewAnswerRepository
                                                        .findBySessionId(session.getId());

                                        long totalScore = answers.stream()
                                                        .mapToLong(InterviewAnswer::getScore)
                                                        .sum();

                                        int answeredQuestions = answers.size();

                                        long maximumScore = session.getTotalQuestions() * 10L;

                                        double percentage = maximumScore == 0
                                                        ? 0
                                                        : (totalScore * 100.0)
                                                                        / maximumScore;

                                        return new InterviewHistoryResponse(
                                                        session.getId(),
                                                        session.getCategory().getName(),
                                                        session.getDifficulty().name(),
                                                        session.getTotalQuestions(),
                                                        answeredQuestions,
                                                        totalScore,
                                                        maximumScore,
                                                        percentage,
                                                        session.getStatus(),
                                                        session.getStartedAt(),
                                                        session.getCompletedAt());
                                })
                                .toList();
        }
}
