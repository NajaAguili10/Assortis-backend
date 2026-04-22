package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DonorRepository extends JpaRepository<Donor, Long>, JpaSpecificationExecutor<Donor> {
}
