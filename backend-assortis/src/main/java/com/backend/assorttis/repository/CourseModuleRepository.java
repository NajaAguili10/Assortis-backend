package com.backend.assorttis.repository;

import com.backend.assorttis.entities.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseModuleRepository
        extends JpaRepository<CourseModule, Long>, JpaSpecificationExecutor<CourseModule> {
}
