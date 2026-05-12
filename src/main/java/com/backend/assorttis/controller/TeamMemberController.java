package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationTeamInvitationRequest;
import com.backend.assorttis.dto.organization.OrganizationTeamMemberUpdateRequest;
import com.backend.assorttis.dto.organization.OrganizationTeamMembersDTO;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @GetMapping("/team-members")
    public OrganizationTeamMembersDTO getCurrentOrganizationTeamMembers(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        log.info("Controller, getCurrentOrganizationTeamMembers");
        return teamMemberService.getTeamMembersForUser(userDetails.getId());
    }

    @GetMapping("/teams/organization/{organizationId}")
    public OrganizationTeamMembersDTO getOrganizationTeamMembers(@PathVariable Long organizationId) {
        log.info("Controller, getOrganizationTeamMembers");
        log.info("organization ID: {}", organizationId);
        return teamMemberService.getTeamMembersForOrganization(organizationId);
    }

    @GetMapping("/team-members/departments")
    public List<String> getAvailableDepartments(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return teamMemberService.getAvailableDepartmentsForUser(userDetails.getId());
    }

    @PutMapping("/team-members/{memberUserId}")
    public OrganizationTeamMembersDTO.TeamMemberDTO updateCurrentOrganizationMember(
            @PathVariable Long memberUserId,
            @RequestBody OrganizationTeamMemberUpdateRequest request,
            Authentication authentication
    ) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return teamMemberService.updateCurrentOrganizationMember(userDetails.getId(), memberUserId, request);
    }

    @PostMapping("/team-members/invitations")
    public Map<String, Long> inviteCurrentOrganizationExpert(
            @RequestBody OrganizationTeamInvitationRequest request,
            Authentication authentication
    ) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        Long invitationId = teamMemberService.inviteCurrentOrganizationExpert(userDetails.getId(), request);
        return Map.of("id", invitationId);
    }

    @DeleteMapping("/team-members/{memberUserId}")
    public void deleteCurrentOrganizationMember(
            @PathVariable Long memberUserId,
            Authentication authentication
    ) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        teamMemberService.deleteCurrentOrganizationMember(userDetails.getId(), memberUserId);
    }
}
