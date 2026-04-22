package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ValueProposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ValuePropositionRepository
        extends JpaRepository<ValueProposition, Long>, JpaSpecificationExecutor<ValueProposition> {
}
