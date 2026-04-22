package com.backend.assorttis.repository;

import com.backend.assorttis.entities.MyProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MyProjectRepository extends JpaRepository<MyProject, Long>, JpaSpecificationExecutor<MyProject> {
}
