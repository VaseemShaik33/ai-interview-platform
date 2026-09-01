package com.aiinterview.platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.InterviewCategory;

public interface InterviewCategoryRepository extends JpaRepository<InterviewCategory,Long> {
       Optional<InterviewCategory> findByName(String name);
}
