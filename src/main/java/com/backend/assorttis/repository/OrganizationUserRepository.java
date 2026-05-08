package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.OrganizationUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrganizationUserRepository
        extends JpaRepository<OrganizationUser, OrganizationUserId>, JpaSpecificationExecutor<OrganizationUser> {




    @Query("""
            select ou from OrganizationUser ou
            join fetch ou.organization
            join fetch ou.user
            where ou.id.userId = :userId
            order by ou.joinedAt asc
            """)
    List<OrganizationUser> findMembershipsByUserId(@Param("userId") Long userId);
    

    @Query("""
            SELECT ou.organization.id FROM OrganizationUser ou
            WHERE LOWER(ou.user.email) = LOWER(:email)
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    List<Long> findActiveOrganizationIdsByUserEmail(@Param("email") String email);
    java.util.Optional<OrganizationUser> findByUserId(Long userId);
    @Query("""
            SELECT ou FROM OrganizationUser ou
            JOIN FETCH ou.user user
            WHERE ou.organization.id = :organizationId
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    List<OrganizationUser> findActiveUsersByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("""
            SELECT ou FROM OrganizationUser ou
            WHERE ou.user.id = :userId
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    List<OrganizationUser> findActiveByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT ou FROM OrganizationUser ou
            WHERE ou.user.id = :userId
              AND (ou.membershipStatus IS NULL OR LOWER(ou.membershipStatus) = 'active')
            """)
    Optional<OrganizationUser> findFirstByUserId(@Param("userId") Long userId);


    @Query("""
            select ou from OrganizationUser ou
            join fetch ou.organization
            join fetch ou.user
            where ou.organization.id = :organizationId
            order by ou.joinedAt asc
            """)
    List<OrganizationUser> findMembersByOrganizationId(@Param("organizationId") Long organizationId);
}
