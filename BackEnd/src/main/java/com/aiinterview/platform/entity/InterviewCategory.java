package com.aiinterview.platform.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="interview_categories")
public class InterviewCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interview_id")
    private Long id;

     @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable=false)
    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
