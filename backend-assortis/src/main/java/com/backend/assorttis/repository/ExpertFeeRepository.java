package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ExpertFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpertFeeRepository extends JpaRepository<ExpertFee, Long>, JpaSpecificationExecutor<ExpertFee> {
}
