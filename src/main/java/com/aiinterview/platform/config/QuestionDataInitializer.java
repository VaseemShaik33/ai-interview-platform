package com.aiinterview.platform.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aiinterview.platform.entity.Difficulty;
import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.entity.Question;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.repository.QuestionRepository;

@Component
@Order(2)
public class QuestionDataInitializer implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final InterviewCategoryRepository interviewCategoryRepository;

    public QuestionDataInitializer(
            QuestionRepository questionRepository,
            InterviewCategoryRepository interviewCategoryRepository) {

        this.questionRepository = questionRepository;
        this.interviewCategoryRepository = interviewCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, String> questionFiles = new LinkedHashMap<>();

        questionFiles.put("java.csv", "Java");
        questionFiles.put("spring-boot.csv", "Spring Boot");
        questionFiles.put("backend.csv", "Backend Development");
        questionFiles.put("frontend.csv", "Frontend Development");
        questionFiles.put("mern-stack.csv", "MERN Stack");
        questionFiles.put("sql.csv", "SQL");
        questionFiles.put("dbms.csv", "DBMS");
        questionFiles.put("dsa.csv", "DSA");
        questionFiles.put("operating-systems.csv", "Operating Systems");
        questionFiles.put("computer-networks.csv", "Computer Networks");
        questionFiles.put("system-design.csv", "System Design");
        questionFiles.put("devops.csv", "DevOps");
        questionFiles.put("aws-cloud.csv", "AWS / Cloud");
        questionFiles.put("react.csv", "React");
        questionFiles.put("javascript.csv", "JavaScript");

        for (Map.Entry<String, String> entry : questionFiles.entrySet()) {

            String fileName = entry.getKey();
            String categoryName = entry.getValue();

            loadQuestions(fileName, categoryName);
        }
    }

    private void loadQuestions(String fileName, String categoryName)
            throws Exception {

        InterviewCategory category =
                interviewCategoryRepository.findByName(categoryName)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found: " + categoryName));

        long existingCount =
                questionRepository.countByCategory(category);

        if (existingCount > 0) {
            System.out.println(
                    "Skipping " + categoryName +
                    " because questions already exist.");
            return;
        }

        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "question-bank/" + fileName);

        if (inputStream == null) {
            throw new RuntimeException(
                    "Question file not found: " + fileName);
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     inputStream,
                                     StandardCharsets.UTF_8))) {

            // Skip header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", 2);

                if (parts.length < 2) {
                    continue;
                }

                String difficultyText = parts[0].trim();
                String questionText = parts[1].trim();

                Difficulty difficulty =
                        Difficulty.valueOf(
                                difficultyText.toUpperCase());

                Question question = new Question();

                question.setQuestionText(questionText);
                question.setDifficulty(difficulty);
                question.setCategory(category);

                questionRepository.save(question);
            }
        }

        System.out.println(
                "Loaded questions for: " + categoryName);
    }
}