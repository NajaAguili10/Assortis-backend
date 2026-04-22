package com.backend.assorttis.repository;

import com.backend.assorttis.entities.TenderLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenderLotRepository extends JpaRepository<TenderLot, Long>, JpaSpecificationExecutor<TenderLot> {
}
