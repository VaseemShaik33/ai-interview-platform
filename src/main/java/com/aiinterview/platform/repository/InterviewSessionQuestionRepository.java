package com.aiinterview.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.InterviewSession;
import com.aiinterview.platform.entity.InterviewSessionQuestion;

public interface InterviewSessionQuestionRepository
        extends JpaRepository<InterviewSessionQuestion, Long> {

    List<InterviewSessionQuestion> findBySessionOrderByQuestionOrder(
            InterviewSession session
    );

    Optional<InterviewSessionQuestion> findBySessionAndQuestionOrder(
            InterviewSession session,
            int questionOrder
    );
}