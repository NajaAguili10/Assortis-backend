package com.backend.assorttis.dto.organization;

import lombok.Data;

@Data
public class OrganizationInvitationCreateRequest {
    private String recipientType;
    private Long organizationId;
    private Long expertId;
    private String invitationType;
    private String email;
    private String subject;
    private String message;
    private Long tenderId;
    private Long projectId;
}
