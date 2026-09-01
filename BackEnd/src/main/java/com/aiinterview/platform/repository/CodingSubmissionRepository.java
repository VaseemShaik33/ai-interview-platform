package com.aiinterview.platform.repository;

import com.aiinterview.platform.entity.CodingSubmission;
import com.aiinterview.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {
    List<CodingSubmission> findTop20ByUserOrderBySubmittedAtDesc(User user);

    long countByUserAndStatus(User user, com.aiinterview.platform.entity.SubmissionStatus status);
}
