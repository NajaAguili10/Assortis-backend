package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
}
