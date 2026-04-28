package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment> {

    @Query("""
            SELECT COUNT(e)
            FROM Enrollment e
            WHERE e.completedAt IS NOT NULL
              AND e.user.id IN :userIds
            """)
    Long countCompletedTrainingsByUserIds(@Param("userIds") Collection<Long> userIds);
}
