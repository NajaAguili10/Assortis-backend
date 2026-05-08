package com.backend.assorttis.repository;

import com.backend.assorttis.entities.SessionEnrollment;
import com.backend.assorttis.entities.SessionEnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SessionEnrollmentRepository
        extends JpaRepository<SessionEnrollment, SessionEnrollmentId>, JpaSpecificationExecutor<SessionEnrollment> {
}
