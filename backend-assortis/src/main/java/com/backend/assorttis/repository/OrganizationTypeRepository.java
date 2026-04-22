package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationTypeRepository
        extends JpaRepository<OrganizationType, String>, JpaSpecificationExecutor<OrganizationType> {
}
