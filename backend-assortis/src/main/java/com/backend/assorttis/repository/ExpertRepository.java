package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.backend.assorttis.entities.User;
import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    Optional<Expert> findByUser(User user);
}
