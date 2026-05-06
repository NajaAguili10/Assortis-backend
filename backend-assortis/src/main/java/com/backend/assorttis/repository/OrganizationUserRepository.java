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
            select ou from OrganizationUser ou
            join fetch ou.organization
            join fetch ou.user
            where ou.id.userId = :userId
            order by ou.joinedAt asc
            """)
    List<OrganizationUser> findMembershipsByUserId(@Param("userId") Long userId);

    @Query("""
            select ou from OrganizationUser ou
            join fetch ou.organization
            join fetch ou.user
            where ou.id.organizationId = :organizationId
            order by ou.joinedAt asc
            """)
    List<OrganizationUser> findMembersByOrganizationId(@Param("organizationId") Long organizationId);
}
