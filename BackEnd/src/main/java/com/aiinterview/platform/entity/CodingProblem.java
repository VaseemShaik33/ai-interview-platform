package com.aiinterview.platform.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coding_problems")
public class CodingProblem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CodingDifficulty difficulty;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "constraints_text", columnDefinition = "TEXT")
    private String constraintsText;

    @Column(name = "examples_text", columnDefinition = "TEXT")
    private String examplesText;

    @Column(name = "starter_code", columnDefinition = "TEXT")
    private String starterCode;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodingTestCase> testCases = new ArrayList<>();

    public CodingProblem() {
    }

    public CodingProblem(String title, CodingDifficulty difficulty, String description,
            String constraintsText, String examplesText, String starterCode) {
        this.title = title;
        this.difficulty = difficulty;
        this.description = description;
        this.constraintsText = constraintsText;
        this.examplesText = examplesText;
        this.starterCode = starterCode;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public CodingDifficulty getDifficulty() {
        return difficulty;
    }

    public String getDescription() {
        return description;
    }

    public String getConstraintsText() {
        return constraintsText;
    }

    public String getExamplesText() {
        return examplesText;
    }

    public String getStarterCode() {
        return starterCode;
    }

    public List<CodingTestCase> getTestCases() {
        return testCases;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDifficulty(CodingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConstraintsText(String constraintsText) {
        this.constraintsText = constraintsText;
    }

    public void setExamplesText(String examplesText) {
        this.examplesText = examplesText;
    }

    public void setStarterCode(String starterCode) {
        this.starterCode = starterCode;
    }

    public void addTestCase(CodingTestCase testCase) {
        testCases.add(testCase);
        testCase.setProblem(this);
    }
}
