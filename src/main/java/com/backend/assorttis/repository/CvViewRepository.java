package com.backend.assorttis.repository;

import com.backend.assorttis.entities.CvView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CvViewRepository extends JpaRepository<CvView, Long>, JpaSpecificationExecutor<CvView> {
}
