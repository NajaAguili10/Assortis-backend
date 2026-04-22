package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Consortium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsortiumRepository extends JpaRepository<Consortium, Long>, JpaSpecificationExecutor<Consortium> {
}
