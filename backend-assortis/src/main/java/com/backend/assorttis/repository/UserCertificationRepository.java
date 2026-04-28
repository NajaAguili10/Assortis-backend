package com.backend.assorttis.repository;

import com.backend.assorttis.dto.training.OrganizationCertificationResponseDto;
import com.backend.assorttis.entities.UserCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCertificationRepository
        extends JpaRepository<UserCertification, Long>, JpaSpecificationExecutor<UserCertification> {

    @Query("""
            SELECT COUNT(uc)
            FROM UserCertification uc
            WHERE uc.user.id IN :userIds
            """)
    long countByUserIds(@Param("userIds") List<Long> userIds);

    @Query("""
            SELECT new com.backend.assorttis.dto.training.OrganizationCertificationResponseDto(
                uc.id,
                c.id,
                COALESCE(c.title, uc.name),
                uc.name,
                uc.issuingOrganization,
                uc.issueDate,
                uc.expiryDate,
                uc.credentialId,
                uc.credentialUrl,
                uc.createdAt,
                u.id,
                u.firstName,
                u.lastName,
                u.email
            )
            FROM UserCertification uc
            JOIN uc.user u
            LEFT JOIN uc.course c
            WHERE u.id IN (
                SELECT ou.user.id
                FROM OrganizationUser ou
                WHERE ou.organization.id = :organizationId
            )
            ORDER BY uc.issueDate DESC, uc.createdAt DESC
            """)
    List<OrganizationCertificationResponseDto> findCertificationsByOrganizationId(
            @Param("organizationId") Long organizationId
    );
}
