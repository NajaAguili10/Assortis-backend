package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipMilestoneRepository
        extends JpaRepository<PartnershipMilestone, Long>, JpaSpecificationExecutor<PartnershipMilestone> {
}
