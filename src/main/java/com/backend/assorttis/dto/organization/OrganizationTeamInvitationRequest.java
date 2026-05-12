package com.backend.assorttis.dto.organization;

import lombok.Data;

@Data
public class OrganizationTeamInvitationRequest {
    private Long expertId;
    private String email;
    private String role;
    private String department;
    private String message;
}

