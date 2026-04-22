package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectCommentRepository
        extends JpaRepository<ProjectComment, Long>, JpaSpecificationExecutor<ProjectComment> {
}
