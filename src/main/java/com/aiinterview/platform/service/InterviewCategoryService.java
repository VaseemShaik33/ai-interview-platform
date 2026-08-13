package com.aiinterview.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiinterview.platform.dto.InterviewCategoryResponse;
import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.repository.InterviewCategoryRepository;

@Service

public class InterviewCategoryService {

    private InterviewCategoryRepository interviewCategoryRepository;

    public InterviewCategoryService(InterviewCategoryRepository interviewCategoryRepository){
        this.interviewCategoryRepository=interviewCategoryRepository;
    }
    
    public List<InterviewCategoryResponse> getAllCategories(){
       List <InterviewCategory> categories=interviewCategoryRepository.findAll();
      
       return categories.stream()
       .map(category->new InterviewCategoryResponse(
        category.getId(),
         category.getName(),
         category.getDescription()
       )).toList();

    }

}
