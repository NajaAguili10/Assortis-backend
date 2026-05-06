package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Amendment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AmendmentRepository extends JpaRepository<Amendment, Long>, JpaSpecificationExecutor<Amendment> {
}
