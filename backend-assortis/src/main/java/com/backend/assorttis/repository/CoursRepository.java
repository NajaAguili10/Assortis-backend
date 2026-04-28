package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursRepository extends JpaRepository<Cours, Long>, JpaSpecificationExecutor<Cours> {

    @Query("""
            SELECT DISTINCT c
            FROM Cours c
            LEFT JOIN FETCH c.expert e
            LEFT JOIN FETCH e.user u
            WHERE c.status IS NULL OR LOWER(c.status) = 'published'
            ORDER BY c.createdAt DESC
            """)
    List<Cours> findPublishedCoursesForCatalog();
}
