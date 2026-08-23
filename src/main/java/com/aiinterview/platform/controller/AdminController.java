package com.aiinterview.platform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.entity.Question;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.repository.QuestionRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final InterviewCategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public AdminController(
            InterviewCategoryRepository categoryRepository,
            QuestionRepository questionRepository) {

        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    // =========================
    // CATEGORY APIs
    // =========================

    @PostMapping("/categories")
    public ResponseEntity<InterviewCategory> createCategory(
            @RequestBody InterviewCategory category) {

        InterviewCategory saved = categoryRepository.save(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/categories")
    public List<InterviewCategory> getCategories() {

        return categoryRepository.findAll();
    }

    // =========================
    // QUESTION APIs
    // =========================

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(
            @RequestBody Question question) {

        Question saved = questionRepository.save(question);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() {

        return questionRepository.findAll();
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id) {

        if (!questionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        questionRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}