package com.aiinterview.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiinterview.platform.dto.InterviewCategoryResponse;
import com.aiinterview.platform.service.InterviewCategoryService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class InterviewCategoryController {
    
    private InterviewCategoryService interviewCategoryService;

    public InterviewCategoryController(InterviewCategoryService interviewCategoryService){
        this.interviewCategoryService=interviewCategoryService;
    }
    @GetMapping("/categories")
    public List<InterviewCategoryResponse> getAllCategories( ) {
      List<InterviewCategoryResponse> response=interviewCategoryService.getAllCategories();
      return response;
    }
    
}
