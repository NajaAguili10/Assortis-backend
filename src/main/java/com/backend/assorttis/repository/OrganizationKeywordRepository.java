package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationKeyword;
import com.backend.assorttis.entities.OrganizationKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationKeywordRepository extends JpaRepository<OrganizationKeyword, OrganizationKeywordId>,
        JpaSpecificationExecutor<OrganizationKeyword> {
}
