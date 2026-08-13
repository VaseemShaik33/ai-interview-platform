package com.aiinterview.platform.service;

import org.springframework.stereotype.Service;

@Service
public class QuestionGenerationService {
    private final InterviewCategoryService interviewCategoryService;

    public QuestionGenerationService(InterviewCategoryService interviewCategoryService){
        this.interviewCategoryService=interviewCategoryService;
    }
}
