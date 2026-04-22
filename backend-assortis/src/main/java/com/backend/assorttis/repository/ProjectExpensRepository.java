package com.backend.assorttis.repository;


import com.backend.assorttis.entities.ProjectExpens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface ProjectExpensRepository extends JpaRepository<ProjectExpens, Long> {

    @Query("SELECT COALESCE(SUM(pe.amount), 0) FROM ProjectExpens pe WHERE pe.project.id = :projectId")
    BigDecimal sumSpentBudgetByProjectId(@Param("projectId") Long projectId);
}
