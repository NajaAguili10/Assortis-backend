package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertKeyword;
import com.backend.assorttis.entities.ExpertKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertKeywordRepository
        extends JpaRepository<ExpertKeyword, ExpertKeywordId>, JpaSpecificationExecutor<ExpertKeyword> {
}
