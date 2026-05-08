package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TaskDependency;
import com.backend.assorttis.entities.TaskDependencyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskDependencyRepository
        extends JpaRepository<TaskDependency, TaskDependencyId>, JpaSpecificationExecutor<TaskDependency> {
}
