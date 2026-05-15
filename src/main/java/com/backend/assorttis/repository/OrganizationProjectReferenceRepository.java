package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationProjectReference;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrganizationProjectReferenceRepository
        extends JpaRepository<OrganizationProjectReference, Long>, JpaSpecificationExecutor<OrganizationProjectReference> {

    @EntityGraph(attributePaths = {"country", "region", "sector", "subsector", "donor", "documents"})
    List<OrganizationProjectReference> findByOrganizationIdOrderByUpdatedAtDesc(Long organizationId);

    @EntityGraph(attributePaths = {"country", "region", "sector", "subsector", "donor", "documents"})
    Optional<OrganizationProjectReference> findByIdAndOrganizationId(Long id, Long organizationId);

    long countByOrganizationId(Long organizationId);
}
