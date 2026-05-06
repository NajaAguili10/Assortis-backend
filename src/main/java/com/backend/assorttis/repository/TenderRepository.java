package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.backend.assorttis.entities.Tender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, Long> {

    // Tenders actifs (publiés, deadline future, non annulés)
    @Query("SELECT t FROM Tender t WHERE t.status.code = 'PUBLISHED' AND t.deadline >= CURRENT_DATE AND t.isCancelled = false")
    Page<Tender> findAllActive(Pageable pageable);

    // Compter les tenders par statut
    Long countByStatusCode(String statusCode);

    // Valeur moyenne du budget estimé
    @Query("SELECT AVG(t.estimatedBudget) FROM Tender t WHERE t.estimatedBudget IS NOT NULL")
    Double averageEstimatedBudget();

    // Recherche avec filtres (exemple simple)
    @Query("SELECT t FROM Tender t WHERE " +
            "(:sector IS NULL OR t.mainSector.code = :sector) AND " +
            "(:country IS NULL OR t.country.code = :country) AND " +
            "(:deadlineAfter IS NULL OR t.deadline >= :deadlineAfter)")
    Page<Tender> findByFilters(@Param("sector") String sector,
                               @Param("country") String country,
                               @Param("deadlineAfter") LocalDate deadlineAfter,
                               Pageable pageable);

    @Query("SELECT t FROM Tender t WHERE t.status.code = 'PUBLISHED' AND t.isCancelled = false AND t.deadline >= :deadlineAfter " +
            "AND (:sector IS NULL OR t.mainSector.code = :sector) " +
            "AND (:country IS NULL OR t.country.code = :country)")
    Page<Tender> findActiveTenders(@Param("sector") String sector,
                                   @Param("country") String country,
                                   @Param("deadlineAfter") LocalDate deadlineAfter,
                                   Pageable pageable);
}
