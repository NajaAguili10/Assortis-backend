package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationService;
import com.backend.assorttis.entities.OrganizationServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationServiceRepository extends JpaRepository<OrganizationService, OrganizationServiceId>,
        JpaSpecificationExecutor<OrganizationService> {
    java.util.List<OrganizationService> findByOrganizationId(Long organizationId);
}
