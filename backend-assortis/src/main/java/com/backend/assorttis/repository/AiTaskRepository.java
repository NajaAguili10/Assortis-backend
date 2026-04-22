package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AiTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AiTaskRepository extends JpaRepository<AiTask, Long>, JpaSpecificationExecutor<AiTask> {
}
