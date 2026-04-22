package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatingRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {
}
