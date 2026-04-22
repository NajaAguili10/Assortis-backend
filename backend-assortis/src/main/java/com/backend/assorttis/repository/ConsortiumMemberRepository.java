package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ConsortiumMember;
import com.backend.assorttis.entities.ConsortiumMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsortiumMemberRepository
        extends JpaRepository<ConsortiumMember, ConsortiumMemberId>, JpaSpecificationExecutor<ConsortiumMember> {
}
