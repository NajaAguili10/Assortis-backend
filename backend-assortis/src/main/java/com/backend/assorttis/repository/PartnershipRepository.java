package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartnershipRepository extends JpaRepository<Partnership, Long>, JpaSpecificationExecutor<Partnership> {
}
