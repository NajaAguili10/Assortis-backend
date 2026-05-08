package com.backend.assorttis.repository;

import com.backend.assorttis.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {
    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.jobOffer.id = :jobOfferId")
    long countByJobOfferId(@Param("jobOfferId") Long jobOfferId);
}
