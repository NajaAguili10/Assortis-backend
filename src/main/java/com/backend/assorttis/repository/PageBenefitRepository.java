package com.backend.assorttis.repository;

import com.backend.assorttis.entities.PageBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PageBenefitRepository extends JpaRepository<PageBenefit, Long>, JpaSpecificationExecutor<PageBenefit> {
}
