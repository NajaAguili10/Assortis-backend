package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationRepository
        extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
}
