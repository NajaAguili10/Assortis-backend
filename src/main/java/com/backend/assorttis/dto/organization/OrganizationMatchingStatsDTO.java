package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationMatchingStatsDTO {
    private long available;
    private long highMatches;
    private long avgScore;
    private long totalSaved;
    private long thisMonth;
    private long dossierHighMatches;
}
