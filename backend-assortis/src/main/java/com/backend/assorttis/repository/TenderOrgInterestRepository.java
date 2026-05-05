package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderOrgInterest;
import com.backend.assorttis.entities.TenderOrgInterestId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;

public interface TenderOrgInterestRepository extends JpaRepository<TenderOrgInterest, TenderOrgInterestId> {

    @Query("SELECT SUM(toi.expectedValue) FROM TenderOrgInterest toi WHERE toi.organization.id = :orgId AND toi.pipelineStage IS NOT NULL")
    BigDecimal sumPipelineValueByOrganization(@Param("orgId") Long organizationId);

    @Query(value = """
            SELECT toi FROM TenderOrgInterest toi
            JOIN FETCH toi.tender tender
            LEFT JOIN FETCH tender.publishedByOrganization publishedByOrganization
            LEFT JOIN FETCH tender.status status
            LEFT JOIN FETCH tender.mainSector mainSector
            LEFT JOIN FETCH tender.country country
            LEFT JOIN FETCH tender.donor donor
            JOIN FETCH toi.organization organization
            WHERE toi.pipelineStage IS NOT NULL
              AND (:stage IS NULL OR LOWER(toi.pipelineStage) = LOWER(:stage))
              AND (
                :search IS NULL
                OR LOWER(tender.title) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(tender.referenceCode) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(organization.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(publishedByOrganization.name, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """,
            countQuery = """
            SELECT COUNT(toi) FROM TenderOrgInterest toi
            JOIN toi.tender tender
            LEFT JOIN tender.publishedByOrganization publishedByOrganization
            JOIN toi.organization organization
            WHERE toi.pipelineStage IS NOT NULL
              AND (:stage IS NULL OR LOWER(toi.pipelineStage) = LOWER(:stage))
              AND (
                :search IS NULL
                OR LOWER(tender.title) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(tender.referenceCode) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(organization.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(publishedByOrganization.name, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """)
    Page<TenderOrgInterest> searchAllPipeline(
            @Param("search") String search,
            @Param("stage") String stage,
            Pageable pageable
    );

    @Query(value = """
            SELECT toi FROM TenderOrgInterest toi
            JOIN FETCH toi.tender tender
            LEFT JOIN FETCH tender.publishedByOrganization publishedByOrganization
            LEFT JOIN FETCH tender.status status
            LEFT JOIN FETCH tender.mainSector mainSector
            LEFT JOIN FETCH tender.country country
            LEFT JOIN FETCH tender.donor donor
            JOIN FETCH toi.organization organization
            WHERE toi.pipelineStage IS NOT NULL
              AND organization.id IN :organizationIds
              AND (:stage IS NULL OR LOWER(toi.pipelineStage) = LOWER(:stage))
              AND (
                :search IS NULL
                OR LOWER(tender.title) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(tender.referenceCode) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(organization.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(publishedByOrganization.name, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """,
            countQuery = """
            SELECT COUNT(toi) FROM TenderOrgInterest toi
            JOIN toi.tender tender
            LEFT JOIN tender.publishedByOrganization publishedByOrganization
            JOIN toi.organization organization
            WHERE toi.pipelineStage IS NOT NULL
              AND organization.id IN :organizationIds
              AND (:stage IS NULL OR LOWER(toi.pipelineStage) = LOWER(:stage))
              AND (
                :search IS NULL
                OR LOWER(tender.title) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(tender.referenceCode) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(organization.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(publishedByOrganization.name, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """)
    Page<TenderOrgInterest> searchPipelineForOrganizations(
            @Param("organizationIds") Collection<Long> organizationIds,
            @Param("search") String search,
            @Param("stage") String stage,
            Pageable pageable
    );
}
