package com.backend.assorttis.repository;

import com.backend.assorttis.entities.UserCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCertificationRepository
        extends JpaRepository<UserCertification, Long>, JpaSpecificationExecutor<UserCertification> {
}
