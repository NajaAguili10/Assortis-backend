package com.backend.assorttis.repository;

import com.backend.assorttis.entities.LiveSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LiveSessionRepository extends JpaRepository<LiveSession, Long>, JpaSpecificationExecutor<LiveSession> {
}
