package com.backend.assorttis.repository;

import com.backend.assorttis.entities.ContractLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractLotRepository extends JpaRepository<ContractLot, Long>, JpaSpecificationExecutor<ContractLot> {
}
