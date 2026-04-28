package com.backend.assorttis.service;

import com.backend.assorttis.dto.training.OrganizationCertificationResponseDto;

import java.util.List;

public interface OrganizationCertificationService {

    List<OrganizationCertificationResponseDto> getOrganizationCertifications(Long organizationId);

    String getCertificationCredentialUrl(Long certificationId);
}
