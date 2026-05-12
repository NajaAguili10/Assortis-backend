package com.backend.assorttis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Table(name = "organization_project_reference_docs")
@Getter @Setter @NoArgsConstructor
public class OrganizationProjectReferenceDocument {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_reference_id", nullable = false)
    private OrganizationProjectReference projectReference;

    private String name;

    @Column(length = 20)
    private String type; // tor, report

    @Column(name = "uploaded_at")
    private Instant uploadedAt = Instant.now();

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    private Long size;

    @Column(name = "content_data_url", length = 1000)
    private String contentDataUrl;
}
