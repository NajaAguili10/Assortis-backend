package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderOrgInterest;
import com.backend.assorttis.entities.TenderOrgInterestId;
import org.springframework.data.jpa.repository.JpaRepository;


import com.backend.assorttis.entities.TenderOrgInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface TenderOrgInterestRepository extends JpaRepository<TenderOrgInterest, Long> {

    // Valeur totale du pipeline pour une organisation donnée
    @Query("SELECT SUM(toi.expectedValue) FROM TenderOrgInterest toi WHERE toi.organization.id = :orgId AND toi.pipelineStage IS NOT NULL")
    BigDecimal sumPipelineValueByOrganization(@Param("orgId") Long organizationId);
}
