package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;

public interface OrganizationRepository
        extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    long countByIsActiveTrue();
    long countByValidatedTrue();

    @Query("SELECT COUNT(DISTINCT o.country) FROM Organization o")
    long countDistinctCountry();
}
