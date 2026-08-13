package com.aiinterview.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiinterview.platform.entity.Difficulty;
import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.entity.Question;

public interface QuestionRepository  extends JpaRepository<Question,Long>{
    List<Question> findByCategoryAndDifficulty(
        InterviewCategory category,
        Difficulty difficulty
);
long countByCategory(InterviewCategory category);
}
