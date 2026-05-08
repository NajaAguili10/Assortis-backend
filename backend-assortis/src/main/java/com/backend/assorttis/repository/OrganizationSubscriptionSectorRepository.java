package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationSubscriptionSector;
import com.backend.assorttis.entities.OrganizationSubscriptionSectorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationSubscriptionSectorRepository extends JpaRepository<OrganizationSubscriptionSector, OrganizationSubscriptionSectorId> {
    List<OrganizationSubscriptionSector> findById_OrganizationId(Long organizationId);
}
