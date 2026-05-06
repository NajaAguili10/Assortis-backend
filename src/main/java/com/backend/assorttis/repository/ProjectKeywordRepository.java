package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ProjectKeyword;
import com.backend.assorttis.entities.ProjectKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectKeywordRepository
        extends JpaRepository<ProjectKeyword, ProjectKeywordId>, JpaSpecificationExecutor<ProjectKeyword> {
}
