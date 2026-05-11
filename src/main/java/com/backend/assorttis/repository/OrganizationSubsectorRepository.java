package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationSubsector;
import com.backend.assorttis.entities.OrganizationSubsectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationSubsectorRepository extends JpaRepository<OrganizationSubsector, OrganizationSubsectorId>,
        JpaSpecificationExecutor<OrganizationSubsector> {
    java.util.List<OrganizationSubsector> findByOrganizationId(Long organizationId);
}
