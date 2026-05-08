package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationProfilePendingChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationProfilePendingChangeRepository
        extends JpaRepository<OrganizationProfilePendingChange, Long>,
        JpaSpecificationExecutor<OrganizationProfilePendingChange> {
}
