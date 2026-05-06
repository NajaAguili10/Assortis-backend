package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationKPIsDTO {
    private long totalOrganizations;
    private long activeOrganizations;
    private long verifiedOrganizations;
    private long partnerships;
    private long newPartnerships;
    private long countriesCovered;
    private long invitations;
    private long pendingInvitations;
}
