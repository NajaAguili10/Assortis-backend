package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationLanguage;
import com.backend.assorttis.entities.OrganizationLanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationLanguageRepository extends JpaRepository<OrganizationLanguage, OrganizationLanguageId>,
        JpaSpecificationExecutor<OrganizationLanguage> {
}
