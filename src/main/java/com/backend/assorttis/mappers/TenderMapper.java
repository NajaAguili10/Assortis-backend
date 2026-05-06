// New mapper class
package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.tender.*;
import com.backend.assorttis.entities.Tender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class TenderMapper {

    public TenderListDTO toTenderListDTO(Tender tender) {
        TenderListDTO dto = new TenderListDTO();
        dto.setId(tender.getId());
        dto.setReferenceNumber(tender.getReferenceCode());
        dto.setTitle(tender.getTitle());
        dto.setOrganizationName(tender.getPublishedByOrganization() != null ? tender.getPublishedByOrganization().getName() : null);
        dto.setCountry(tender.getCountry() != null ? tender.getCountry().getCode() : null);
        dto.setDeadline(tender.getDeadline());
        if (tender.getDeadline() != null) {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), tender.getDeadline());
            dto.setDaysRemaining((int) days);
        }
        // Budget
        if (tender.getEstimatedBudget() != null) {
            MoneyDTO budget = new MoneyDTO();
            budget.setAmount(tender.getEstimatedBudget());
            budget.setCurrency(tender.getCurrency() != null ? tender.getCurrency() : "EUR");
            budget.setFormatted(String.format("%s %,.0f", budget.getCurrency(), tender.getEstimatedBudget()));
            dto.setBudget(budget);
        }
        dto.setStatus(tender.getStatus() != null ? tender.getStatus().getCode() : null);
        dto.setMatchScore(null); // To be computed if needed
        // Sectors – assuming a many-to-many relationship exists; otherwise, use mainSector
        if (tender.getMainSector() != null) {
            dto.setSectors(List.of(tender.getMainSector().getCode()));
        }
        // Sub-sectors – to be implemented via join
        dto.setCreatedAt(tender.getCreatedAt() != null ? tender.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null);
        dto.setProposalsCount(null); // To be counted via additional query
        dto.setInterestedOrgsCount(null);
        return dto;
    }
}
