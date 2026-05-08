package com.backend.assorttis.dto.organization;

import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class OrganizationCertificationDTO {
    private Long id;
    private String certificationName;
    private String issuingOrganization;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String credentialId;
    private String credentialUrl;
    private Instant createdAt;
}
