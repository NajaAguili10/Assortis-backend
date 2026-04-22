package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TeamMember;
import com.backend.assorttis.entities.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamMemberRepository
        extends JpaRepository<TeamMember, TeamMemberId>, JpaSpecificationExecutor<TeamMember> {
}
