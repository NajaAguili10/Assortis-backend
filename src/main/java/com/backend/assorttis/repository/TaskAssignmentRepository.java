package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskAssignmentRepository
        extends JpaRepository<TaskAssignment, Long>, JpaSpecificationExecutor<TaskAssignment> {
}
