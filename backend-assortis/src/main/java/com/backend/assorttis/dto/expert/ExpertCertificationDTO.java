package com.backend.assorttis.dto.expert;

import com.backend.assorttis.entities.Expert;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpertCertificationDTO {

        private Long id;

        private String issuerName;

        private String name;

        private String issuingOrganization;

        private LocalDate issueDate;

        private LocalDate expiryDate;

        private String credentialId;

        private String credentialUrl;

    }
