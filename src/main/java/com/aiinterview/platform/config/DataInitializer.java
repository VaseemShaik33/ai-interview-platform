package com.aiinterview.platform.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aiinterview.platform.entity.InterviewCategory;
import com.aiinterview.platform.repository.InterviewCategoryRepository;
import com.aiinterview.platform.service.InterviewCategoryService;

@Component
public class DataInitializer implements CommandLineRunner{
    
    private final InterviewCategoryRepository interviewCategoryRepository;

    public DataInitializer(InterviewCategoryRepository interviewCategoryRepository){
        this.interviewCategoryRepository=interviewCategoryRepository;
    }


    @Override
    @Order(1)
    public void run(String... args) {

        List<InterviewCategory> categories = interviewCategoryRepository.findAll();

        if (categories.isEmpty()) {

            InterviewCategory java = new InterviewCategory();
            java.setName("Java");
            java.setDescription("Core Java and Java interview questions");

            InterviewCategory springBoot = new InterviewCategory();
            springBoot.setName("Spring Boot");
            springBoot.setDescription("Spring Boot and Spring ecosystem questions");

            InterviewCategory backend = new InterviewCategory();
            backend.setName("Backend Development");
            backend.setDescription("Backend, REST API and server-side questions");

            InterviewCategory frontend = new InterviewCategory();
            frontend.setName("Frontend Development");
            frontend.setDescription("HTML, CSS, JavaScript and frontend questions");

            InterviewCategory mern = new InterviewCategory();
            mern.setName("MERN Stack");
            mern.setDescription("MongoDB, Express.js, React and Node.js questions");

            InterviewCategory sql = new InterviewCategory();
            sql.setName("SQL");
            sql.setDescription("SQL query and database interview questions");

            InterviewCategory dbms = new InterviewCategory();
            dbms.setName("DBMS");
            dbms.setDescription("Database management system interview questions");

            InterviewCategory dsa = new InterviewCategory();
            dsa.setName("DSA");
            dsa.setDescription("Data structures and algorithms interview questions");

            InterviewCategory operatingSystems = new InterviewCategory();
            operatingSystems.setName("Operating Systems");
            operatingSystems.setDescription("Operating system concepts and interview questions");

            InterviewCategory computerNetworks = new InterviewCategory();
            computerNetworks.setName("Computer Networks");
            computerNetworks.setDescription("Computer networking concepts and interview questions");

            InterviewCategory systemDesign = new InterviewCategory();
            systemDesign.setName("System Design");
            systemDesign.setDescription("Low-level and high-level system design questions");

            InterviewCategory devOps = new InterviewCategory();
            devOps.setName("DevOps");
            devOps.setDescription("CI/CD, Docker, Jenkins and DevOps interview questions");

            InterviewCategory aws = new InterviewCategory();
            aws.setName("AWS / Cloud");
            aws.setDescription("AWS and cloud computing interview questions");

            InterviewCategory react = new InterviewCategory();
            react.setName("React");
            react.setDescription("React.js and frontend development interview questions");

            InterviewCategory javascript = new InterviewCategory();
            javascript.setName("JavaScript");
            javascript.setDescription("JavaScript concepts and interview questions");

            interviewCategoryRepository.saveAll(
                    List.of(
                            java,
                            springBoot,
                            backend,
                            frontend,
                            mern,
                            sql,
                            dbms,
                            dsa,
                            operatingSystems,
                            computerNetworks,
                            systemDesign,
                            devOps,
                            aws,
                            react,
                            javascript
                    )
            );
        }
    }



}