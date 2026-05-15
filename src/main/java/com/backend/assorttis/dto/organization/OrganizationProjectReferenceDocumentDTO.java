package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.time.Instant;

@Data
public class OrganizationProjectReferenceDocumentDTO {
    private Long id;
    private String name;
    private String type;
    private Instant uploadedAt;
    private String mimeType;
    private Long size;
    private String contentDataUrl;
}
