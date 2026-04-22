package com.backend.assorttis.repository;

import com.backend.assorttis.entities.AiFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AiFeedbackRepository extends JpaRepository<AiFeedback, Long>, JpaSpecificationExecutor<AiFeedback> {
}
