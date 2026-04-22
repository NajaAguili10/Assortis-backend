package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TenderStatusRepository
        extends JpaRepository<TenderStatus, String>, JpaSpecificationExecutor<TenderStatus> {


    Optional<TenderStatus> findByCode(String code);

}
