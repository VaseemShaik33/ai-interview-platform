package com.aiinterview.platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private Long id;


    private String questionText;

     
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="category_id")
    private InterviewCategory category;

@Enumerated(EnumType.STRING)
private Difficulty difficulty;


    public Long getId() {
    return id;
}

   public void setId(Long id) {
    this.id = id;
   }

   public String getQuestionText() {
    return questionText;
   }

   public void setQuestionText(String questionText) {
    this.questionText = questionText;
   }

   public InterviewCategory getCategory() {
    return category;
   }

   public void setCategory(InterviewCategory category) {
    this.category = category;
   }

   public Difficulty getDifficulty() {
    return difficulty;
   }

   public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
   }

    
}
