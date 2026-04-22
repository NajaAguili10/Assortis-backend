package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvitationRepository extends JpaRepository<Invitation, Long>, JpaSpecificationExecutor<Invitation> {
}
