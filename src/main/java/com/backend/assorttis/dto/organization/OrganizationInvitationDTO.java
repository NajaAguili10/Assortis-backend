package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OrganizationInvitationDTO {
    private Long id;
    private String direction;
    private String from;
    private String to;
    private String invitationType;
    private String status;
    private String subject;
    private String message;
    private Long inviteeExpertId;
    private Long inviteeUserId;
    private String inviteeEmail;
    private Instant sentDate;
    private Instant expiryDate;
}
