package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationInvitationStatsDTO {
    private long received;
    private long sent;
    private long pending;
    private long accepted;
    private long rejected;
    private long expired;
    private long total;
}

