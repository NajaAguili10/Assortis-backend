package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderTor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderTorRepository extends JpaRepository<TenderTor, Long>, JpaSpecificationExecutor<TenderTor> {
}
