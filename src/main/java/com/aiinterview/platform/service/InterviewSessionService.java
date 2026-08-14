package com.aiinterview.platform.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

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
import com.aiinterview.platform.repository.InterviewAnswerRepository;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.repository.InterviewSessionQuestionRepository;
import com.aiinterview.platform.repository.InterviewSessionRepository;
import com.aiinterview.platform.repository.QuestionRepository;

@Service
public class InterviewSessionService {
    private final InterviewCategoryRepository interviewCategoryRepository;

    private final QuestionRepository questionRepository;

    private final InterviewSessionRepository interviewSessionRepository;

    private final InterviewSessionQuestionRepository interviewSessionQuestionRepository;


    private final InterviewAnswerRepository interviewAnswerRepository;

    public InterviewSessionService(InterviewCategoryRepository interviewCategoryRepositor,
            QuestionRepository questionRepository, InterviewSessionRepository interviewSessionRepository,
            InterviewSessionQuestionRepository interviewSessionQuestionRepository,InterviewAnswerRepository interviewAnswerRepository) {
        this.interviewCategoryRepository = interviewCategoryRepositor;
        this.questionRepository = questionRepository;
        this.interviewSessionRepository = interviewSessionRepository;
        this.interviewSessionQuestionRepository = interviewSessionQuestionRepository;
        this.interviewAnswerRepository=interviewAnswerRepository;
    }

   public StartInterviewResponse startInterview(StartInterviewRequest request) {
 System.out.println("START INTERVIEW CALLED");
    // 1. Validate number of questions
    if (request.numberOfQuestions() != 5 &&
        request.numberOfQuestions() != 10 &&
        request.numberOfQuestions() != 15 &&
        request.numberOfQuestions() != 20) {

        throw new RuntimeException("Invalid Number of Questions");
    }

    // 2. Find category
    InterviewCategory category =
            interviewCategoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Invalid category"));

    // 3. Find questions matching category + difficulty
    List<Question> questions =
            questionRepository.findByCategoryAndDifficulty(
                    category,
                    request.difficulty()
            );

    // 4. Check enough questions
    if (questions.size() < request.numberOfQuestions()) {
        throw new RuntimeException("Not Enough Questions");
    }

    // 5. Shuffle questions
    Collections.shuffle(questions);

    // 6. Take required number of questions
    List<Question> selectedQuestions =
            questions.subList(0, request.numberOfQuestions());

    // 7. Create InterviewSession
    InterviewSession session = new InterviewSession();

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

        InterviewSessionQuestion sessionQuestion =
                new InterviewSessionQuestion();

        sessionQuestion.setSession(session);
        sessionQuestion.setQuestion(question);
        sessionQuestion.setQuestionOrder(order);

        interviewSessionQuestionRepository.save(sessionQuestion);

        order++;
    }

    // 9. First question
    Question firstQuestion = selectedQuestions.get(0);

    QuestionResponse questionResponse =
            new QuestionResponse(
                    firstQuestion.getId(),
                    firstQuestion.getQuestionText(),
                    firstQuestion.getCategory().getName(),
                    firstQuestion.getDifficulty()
            );

    // 10. Return Question #1
    return new StartInterviewResponse(
            session.getId(),
            1,
            request.numberOfQuestions(),
            questionResponse
    );
}

public SubmitAnswerResponse submitAnswer(
        Long sessionId,
        SubmitAnswerRequest request) {

    // 1. Find interview session
    InterviewSession session =
            interviewSessionRepository.findById(sessionId)
                    .orElseThrow(() ->
                            new RuntimeException("Interview session not found"));

    // 2. Get current question number
    int currentQuestionNumber =
            session.getCurrentQuestionNumber();

    // 3. Find current question
    InterviewSessionQuestion sessionQuestion =
            interviewSessionQuestionRepository
                    .findBySessionAndQuestionOrder(
                            session,
                            currentQuestionNumber
                    )
                    .orElseThrow(() ->
                            new RuntimeException("Question not found"));

    // 4. Create InterviewAnswer
    InterviewAnswer answer = new InterviewAnswer();

    answer.setSession(session);
    answer.setQuestion(sessionQuestion.getQuestion());
    answer.setUserAnswer(request.userAnswer());

    // Temporary values until Gemini is connected
    answer.setScore(0L);
    answer.setFeedback("AI evaluation pending");
    answer.setCorrectness(Correctness.PENDING);

    // 5. Save answer
    interviewAnswerRepository.save(answer);

    // 6. Check whether this was the last question
    if (currentQuestionNumber == session.getTotalQuestions()) {

        session.setStatus(InterviewStatus.COMPLETED);

        interviewSessionRepository.save(session);

        return new SubmitAnswerResponse(
                session.getId(),
                currentQuestionNumber,
                session.getTotalQuestions(),
                null
        );
    }

    // 7. Move to next question
    int nextQuestionNumber =
            currentQuestionNumber + 1;

    session.setCurrentQuestionNumber(nextQuestionNumber);

    interviewSessionRepository.save(session);

    // 8. Find next question
    InterviewSessionQuestion nextSessionQuestion =
            interviewSessionQuestionRepository
                    .findBySessionAndQuestionOrder(
                            session,
                            nextQuestionNumber
                    )
                    .orElseThrow(() ->
                            new RuntimeException("Next question not found"));

    Question nextQuestion =
            nextSessionQuestion.getQuestion();

    // 9. Convert to QuestionResponse
    QuestionResponse questionResponse =
            new QuestionResponse(
                    nextQuestion.getId(),
                    nextQuestion.getQuestionText(),
                    nextQuestion.getCategory().getName(),
                    nextQuestion.getDifficulty()
            );

    // 10. Return next question
    return new SubmitAnswerResponse(
            session.getId(),
            nextQuestionNumber,
            session.getTotalQuestions(),
            questionResponse
    );
}
}
