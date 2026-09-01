package com.aiinterview.platform.repository;

import com.aiinterview.platform.entity.CodingProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CodingProblemRepository extends JpaRepository<CodingProblem, Long> {
    List<CodingProblem> findAllByOrderByIdAsc();
}
