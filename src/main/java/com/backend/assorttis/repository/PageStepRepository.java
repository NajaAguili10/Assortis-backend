package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PageStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageStepRepository extends JpaRepository<PageStep, Long>, JpaSpecificationExecutor<PageStep> {
}
