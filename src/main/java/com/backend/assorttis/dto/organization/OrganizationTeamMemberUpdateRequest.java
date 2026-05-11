package com.backend.assorttis.dto.organization;

import lombok.Data;

@Data
public class OrganizationTeamMemberUpdateRequest {
    private String role;
    private String department;
    private String status;
}
