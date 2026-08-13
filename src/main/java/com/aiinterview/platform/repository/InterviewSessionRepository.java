package com.aiinterview.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.InterviewSession;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Long>{
    
}
