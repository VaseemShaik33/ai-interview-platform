package com.aiinterview.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.InterviewAnswer;

public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer,Long> {

    
} 
