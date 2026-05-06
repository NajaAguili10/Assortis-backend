package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationTeamMembersDTO;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        log.info("📥 Controller, getCurrentOrganizationTeamMembers");
        return teamMemberService.getTeamMembersForUser(userDetails.getId());
    }

    @GetMapping("/teams/organization/{organizationId}")
    public OrganizationTeamMembersDTO getOrganizationTeamMembers(@PathVariable Long organizationId) {
        log.info("📥 Controller, getOrganizationTeamMembers");
        log.info("📥 organization ID: {}", organizationId);
        return teamMemberService.getTeamMembersForOrganization(organizationId);
    }
}
