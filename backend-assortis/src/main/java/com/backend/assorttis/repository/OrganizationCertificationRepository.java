package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationCertificationRepository
        extends JpaRepository<OrganizationCertification, Long>, JpaSpecificationExecutor<OrganizationCertification> {
}
