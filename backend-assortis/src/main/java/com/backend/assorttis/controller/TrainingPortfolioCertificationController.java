package com.backend.assorttis.controller;

import com.backend.assorttis.dto.training.OrganizationCertificationResponseDto;
import com.backend.assorttis.service.OrganizationCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/training/portfolio")
@RequiredArgsConstructor
public class TrainingPortfolioCertificationController {

    private final OrganizationCertificationService organizationCertificationService;

    @GetMapping("/certifications")
    public ResponseEntity<List<OrganizationCertificationResponseDto>> getOrganizationCertifications(
            @RequestParam Long organizationId
    ) {
        return ResponseEntity.ok(
                organizationCertificationService.getOrganizationCertifications(organizationId)
        );
    }

    @GetMapping("/certifications/{certificationId}/download")
    public ResponseEntity<Void> downloadCertification(
            @PathVariable Long certificationId
    ) {
        String credentialUrl = organizationCertificationService.getCertificationCredentialUrl(certificationId);

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, credentialUrl)
                .build();
    }
}
