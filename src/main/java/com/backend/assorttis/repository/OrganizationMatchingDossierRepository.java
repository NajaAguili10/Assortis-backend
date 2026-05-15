package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationMatchingDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrganizationMatchingDossierRepository
        extends JpaRepository<OrganizationMatchingDossier, Long>, JpaSpecificationExecutor<OrganizationMatchingDossier> {
    List<OrganizationMatchingDossier> findByOrganizationIdOrderByCreatedAtDesc(Long organizationId);

    Optional<OrganizationMatchingDossier> findByIdAndOrganizationId(Long id, Long organizationId);

    long countByOrganizationId(Long organizationId);
}
