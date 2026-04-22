package com.backend.assorttis.repository;

import com.backend.assorttis.entities.JobOfferLanguage;
import com.backend.assorttis.entities.JobOfferLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobOfferLanguageRepository
        extends JpaRepository<JobOfferLanguage, JobOfferLanguageId>, JpaSpecificationExecutor<JobOfferLanguage> {
}
