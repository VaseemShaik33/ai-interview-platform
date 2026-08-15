package com.aiinterview.platform.service;

import org.springframework.stereotype.Service;

import com.aiinterview.platform.dto.GeminiEvaluationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class GeminiEvaluationService {

    private final Client client;

    private final ObjectMapper objectMapper;

    public GeminiEvaluationService(Client client, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    public GeminiEvaluationResponse evaluateAnswer(String question, String userAnswer) {

        String prompt = """
                You are an expert technical interviewer.

                Evaluate the candidate's answer to the interview question.

                Question:
                %s

                Candidate's Answer:
                %s

                Evaluate the answer using these rules:

                1. score:
                   Give a score from 0 to 10.

                2. correctness:
                   Must be exactly one of:
                   CORRECT
                   PARTIAL
                   INCORRECT

                3. feedback:
                   Explain clearly why the answer received this score.

                Return ONLY valid JSON.
                Do not use markdown.
                Do not use ```json.
                Do not add any text before or after the JSON.

                JSON format:
                {
                  "score": 0,
                  "correctness": "CORRECT",
                  "feedback": "..."
                }
                """.formatted(question, userAnswer);

        GenerateContentResponse response = client.models.generateContent(
                "gemini-3.6-flash",
                prompt,
                null);

        String json = response.text();
        try {
            return objectMapper.readValue(
                    json,
                    GeminiEvaluationResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}
