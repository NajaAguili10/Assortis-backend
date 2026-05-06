package com.backend.assorttis.repository;

import com.backend.assorttis.entities.FaqCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FaqCategoryRepository extends JpaRepository<FaqCategory, Long>, JpaSpecificationExecutor<FaqCategory> {
}
