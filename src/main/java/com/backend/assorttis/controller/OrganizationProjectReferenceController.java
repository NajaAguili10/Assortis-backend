package com.backend.assorttis.controller;

import com.backend.assorttis.dto.organization.OrganizationProjectReferenceDTO;
import com.backend.assorttis.dto.organization.OrganizationProjectReferenceRequest;
import com.backend.assorttis.security.services.UserDetailsImpl;
import com.backend.assorttis.service.OrganizationProjectReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/organization-project-references")
@RequiredArgsConstructor
public class OrganizationProjectReferenceController {
    private final OrganizationProjectReferenceService projectReferenceService;

    @GetMapping
    public List<OrganizationProjectReferenceDTO> getReferences(Authentication authentication) {
        return projectReferenceService.getCurrentOrganizationReferences(requireUser(authentication).getId());
    }

    @GetMapping("/{id}")
    public OrganizationProjectReferenceDTO getReference(@PathVariable Long id, Authentication authentication) {
        return projectReferenceService.getCurrentOrganizationReference(requireUser(authentication).getId(), id);
    }

    @PostMapping
    public OrganizationProjectReferenceDTO createReference(
            @RequestBody OrganizationProjectReferenceRequest request,
            Authentication authentication
    ) {
        return projectReferenceService.createCurrentOrganizationReference(requireUser(authentication).getId(), request);
    }

    @PutMapping("/{id}")
    public OrganizationProjectReferenceDTO updateReference(
            @PathVariable Long id,
            @RequestBody OrganizationProjectReferenceRequest request,
            Authentication authentication
    ) {
        return projectReferenceService.updateCurrentOrganizationReference(requireUser(authentication).getId(), id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteReference(@PathVariable Long id, Authentication authentication) {
        projectReferenceService.deleteCurrentOrganizationReference(requireUser(authentication).getId(), id);
    }

    private UserDetailsImpl requireUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication is required");
        }
        return userDetails;
    }
}
