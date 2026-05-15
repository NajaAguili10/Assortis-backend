package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Project;
import com.backend.assorttis.entities.enums.project.ProjectPriorityEnum;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;

public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    // ✅ Count by status (Spring handles it automatically)
    Long countByStatus(ProjectStatus status);

    // ✅ Count urgent projects
    Long countByPriority(ProjectPriorityEnum priority);

    // ✅ Total budget
    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(p.budget), 0) FROM Project p")
    BigDecimal sumTotalBudget();

    // ✅ Spent budget per project
    @org.springframework.data.jpa.repository.Query(
            "SELECT COALESCE(SUM(pe.amount), 0) FROM ProjectExpens pe WHERE pe.project.id = :projectId"
    )
    BigDecimal sumSpentBudgetForProject(Long projectId);
}
