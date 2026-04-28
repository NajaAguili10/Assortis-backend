package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.training.OrganizationCertificationResponseDto;
import com.backend.assorttis.entities.UserCertification;
import com.backend.assorttis.repository.OrganizationRepository;
import com.backend.assorttis.repository.UserCertificationRepository;
import com.backend.assorttis.service.OrganizationCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationCertificationServiceImpl implements OrganizationCertificationService {

    private final OrganizationRepository organizationRepository;
    private final UserCertificationRepository userCertificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationCertificationResponseDto> getOrganizationCertifications(Long organizationId) {

        if (organizationId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "organizationId is required");
        }

        boolean organizationExists = organizationRepository.existsById(organizationId);

        if (!organizationExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Organization not found with id: " + organizationId
            );
        }

        return userCertificationRepository.findCertificationsByOrganizationId(organizationId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getCertificationCredentialUrl(Long certificationId) {

        UserCertification certification = userCertificationRepository.findById(certificationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Certification not found with id: " + certificationId
                ));

        if (certification.getCredentialUrl() == null || certification.getCredentialUrl().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No credential URL available for certification id: " + certificationId
            );
        }

        return certification.getCredentialUrl();
    }
}
