package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoursRepository extends JpaRepository<Cours, Long>, JpaSpecificationExecutor<Cours> {
}
