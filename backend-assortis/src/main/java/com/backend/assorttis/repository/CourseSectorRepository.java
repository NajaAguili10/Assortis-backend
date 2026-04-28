package com.backend.assorttis.repository;

import com.backend.assorttis.entities.CourseSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseSectorRepository extends JpaRepository<CourseSector, CourseSector.CourseSectorId>,
        JpaSpecificationExecutor<CourseSector> {

    @Query("""
            SELECT cs
            FROM CourseSector cs
            JOIN FETCH cs.sector s
            WHERE cs.course.id IN :courseIds
            ORDER BY cs.course.id, s.name
            """)
    List<CourseSector> findByCourseIdsWithSector(@Param("courseIds") List<Long> courseIds);
}
