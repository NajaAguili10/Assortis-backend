package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertLanguage;
import com.backend.assorttis.entities.ExpertLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertLanguageRepository
        extends JpaRepository<ExpertLanguage, ExpertLanguageId>, JpaSpecificationExecutor<ExpertLanguage> {
}
