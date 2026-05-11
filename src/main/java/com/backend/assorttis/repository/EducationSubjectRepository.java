package com.backend.assorttis.repository;

import com.backend.assorttis.entities.EducationSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationSubjectRepository extends JpaRepository<EducationSubject, Long> {
    Optional<EducationSubject> findByNameEn(String nameEn);
}
