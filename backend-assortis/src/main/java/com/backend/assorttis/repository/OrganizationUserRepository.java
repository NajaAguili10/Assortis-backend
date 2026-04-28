package com.backend.assorttis.repository;

import com.backend.assorttis.entities.OrganizationUser;
import com.backend.assorttis.entities.OrganizationUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationUserRepository extends JpaRepository<OrganizationUser, OrganizationUserId>,
        JpaSpecificationExecutor<OrganizationUser> {

    @Query("""
            SELECT ou.user.id
            FROM OrganizationUser ou
            WHERE ou.organization.id = :organizationId
            """)
    List<Long> findUserIdsByOrganizationId(@Param("organizationId") Long organizationId);
}
