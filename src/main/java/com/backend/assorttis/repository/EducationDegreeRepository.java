package com.backend.assorttis.repository;

import com.backend.assorttis.entities.EducationDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationDegreeRepository extends JpaRepository<EducationDegree, Long> {
    Optional<EducationDegree> findByNameEn(String nameEn);
}
