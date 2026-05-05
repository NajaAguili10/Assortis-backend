package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.OrganizationUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationUserRepository
        extends JpaRepository<OrganizationUser, OrganizationUserId>, JpaSpecificationExecutor<OrganizationUser> {
    @Query("""
            SELECT ou.organization.id FROM OrganizationUser ou
            WHERE LOWER(ou.user.email) = LOWER(:email)
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    List<Long> findActiveOrganizationIdsByUserEmail(@Param("email") String email);

    @Query("""
            SELECT ou FROM OrganizationUser ou
            JOIN FETCH ou.user user
            WHERE ou.organization.id = :organizationId
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    List<OrganizationUser> findActiveUsersByOrganizationId(@Param("organizationId") Long organizationId);
}
