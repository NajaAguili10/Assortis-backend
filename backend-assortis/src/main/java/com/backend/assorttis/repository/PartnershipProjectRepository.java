package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipProject;
import com.backend.assorttis.entities.PartnershipProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartnershipProjectRepository
        extends JpaRepository<PartnershipProject, PartnershipProjectId>, JpaSpecificationExecutor<PartnershipProject> {
    @Query("SELECT COUNT(pp) FROM PartnershipProject pp WHERE pp.partnership.id = :partnershipId")
    long countByPartnershipId(@Param("partnershipId") Long partnershipId);
}
