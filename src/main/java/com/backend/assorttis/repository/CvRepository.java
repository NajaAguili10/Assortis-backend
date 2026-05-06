package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CvRepository extends JpaRepository<Cv, Long>, JpaSpecificationExecutor<Cv> {
}
