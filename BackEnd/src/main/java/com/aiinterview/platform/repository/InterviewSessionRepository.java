package com.aiinterview.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.InterviewSession;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Long>{
    List<InterviewSession> findByUserId(Long userId);
    List<InterviewSession> findByUserIdOrderByStartedAtDesc(Long userId);
}
