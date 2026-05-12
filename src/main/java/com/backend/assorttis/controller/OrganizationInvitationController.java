package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationInvitationDTO;
import com.backend.assorttis.dto.organization.OrganizationInvitationStatsDTO;
import com.backend.assorttis.dto.organization.OrganizationInvitationUpdateRequest;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.OrganizationInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/organization-invitations")
@RequiredArgsConstructor
public class OrganizationInvitationController {
    private final OrganizationInvitationService organizationInvitationService;

    @GetMapping
    public List<OrganizationInvitationDTO> getCurrentOrganizationInvitations(Authentication authentication) {
        UserDetailsImpl userDetails = requireUser(authentication);
        return organizationInvitationService.getCurrentOrganizationInvitations(userDetails.getId());
    }

    @GetMapping("/stats")
    public OrganizationInvitationStatsDTO getCurrentOrganizationInvitationStats(Authentication authentication) {
        UserDetailsImpl userDetails = requireUser(authentication);
        return organizationInvitationService.getCurrentOrganizationStats(userDetails.getId());
    }

    @PostMapping("/{invitationId}/accept")
    public OrganizationInvitationDTO acceptInvitation(
            @PathVariable Long invitationId,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = requireUser(authentication);
        return organizationInvitationService.acceptInvitation(userDetails.getId(), invitationId);
    }

    @PostMapping("/{invitationId}/reject")
    public OrganizationInvitationDTO rejectInvitation(
            @PathVariable Long invitationId,
            @RequestBody OrganizationInvitationUpdateRequest request,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = requireUser(authentication);
        return organizationInvitationService.rejectInvitation(userDetails.getId(), invitationId, request);
    }

    @DeleteMapping("/{invitationId}")
    public void deleteInvitation(
            @PathVariable Long invitationId,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = requireUser(authentication);
        organizationInvitationService.deleteInvitation(userDetails.getId(), invitationId);
    }

    private UserDetailsImpl requireUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userDetails;
    }
}

