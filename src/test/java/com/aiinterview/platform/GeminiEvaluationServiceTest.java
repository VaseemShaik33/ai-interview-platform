package com.aiinterview.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aiinterview.platform.dto.GeminiEvaluationResponse;
import com.aiinterview.platform.service.GeminiEvaluationService;

@SpringBootTest
public class GeminiEvaluationServiceTest {
    @Autowired
    private GeminiEvaluationService geminiEvaluationService;

    @Test
    void testGeminiEvaluation() {

        GeminiEvaluationResponse result = geminiEvaluationService.evaluateAnswer(
                "What is dynamic method dispatch in Java?",
                "Dynamic method dispatch is when Java decides at runtime which overridden method should be called based on the actual object.");

        System.out.println("Score: " + result.score());
        System.out.println("Correctness: " + result.correctness());
        System.out.println("Feedback: " + result.feedback());
    }

}