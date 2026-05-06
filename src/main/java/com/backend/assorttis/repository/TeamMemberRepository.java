package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TeamMember;
import com.backend.assorttis.entities.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository
        extends JpaRepository<TeamMember, TeamMemberId>, JpaSpecificationExecutor<TeamMember> {
    @Query("""
            select tm from TeamMember tm
            join fetch tm.team team
            join fetch team.organization
            join fetch tm.user
            where tm.id.userId = :userId
            order by tm.joinedAt asc
            """)
    List<TeamMember> findMembershipsByUserId(@Param("userId") Long userId);

    @Query("""
            select tm from TeamMember tm
            join fetch tm.team team
            join fetch team.organization
            join fetch tm.user
            where team.organization.id = :organizationId
            order by tm.joinedAt asc
            """)
    List<TeamMember> findMembersByOrganizationId(@Param("organizationId") Long organizationId);
}
