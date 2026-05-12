package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartnershipRepository extends JpaRepository<Partnership, Long>, JpaSpecificationExecutor<Partnership> {
    long countByStatusIgnoreCase(String status);

    @Query("""
            select p from Partnership p
            join fetch p.organization
            join fetch p.partnerOrganization
            where p.organization.id = :organizationId
               or p.partnerOrganization.id = :organizationId
            order by p.createdAt desc
            """)
    List<Partnership> findByOrganizationIdOrPartnerOrganizationId(@Param("organizationId") Long organizationId);
}