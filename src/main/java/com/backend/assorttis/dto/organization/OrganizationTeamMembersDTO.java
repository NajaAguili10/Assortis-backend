package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class OrganizationTeamMembersDTO {
    private OrganizationSummaryDTO organization;
    private List<TeamMemberDTO> members;

    @Data
    @Builder
    public static class OrganizationSummaryDTO {
        private Long id;
        private String name;
        private String type;
    }

    @Data
    @Builder
    public static class TeamMemberDTO {
        private Long userId;
        private Long organizationId;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private Boolean admin;
        private String department;
        private String status;
        private Instant joinedAt;
    }
}
