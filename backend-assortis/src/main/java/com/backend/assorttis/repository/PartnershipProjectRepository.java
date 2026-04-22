package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PartnershipProject;
import com.backend.assorttis.entities.PartnershipProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipProjectRepository
        extends JpaRepository<PartnershipProject, PartnershipProjectId>, JpaSpecificationExecutor<PartnershipProject> {
}
