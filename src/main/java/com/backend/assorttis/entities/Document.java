package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "documents", schema = "public")
public class Document {
    private Long id;

    private String ownerType;

    private Long ownerId;

    private String documentType;

    private String title;

    private String fileUrl;

    private BigDecimal fileSizeKb;

    private String mimeType;

    private Integer version;

    private Boolean validated;

    private User validatedBy;

    private Instant validatedAt;

    private DocumentType docTypeCode;

    private BigDecimal ratingAvg;

    private Integer ratingsCount;

    private Integer downloadCount;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @Column(name = "owner_type", length = 50)
    public String getOwnerType() {
        return ownerType;
    }

    @Column(name = "owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    @Size(max = 50)
    @Column(name = "document_type", length = 50)
    public String getDocumentType() {
        return documentType;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "file_url", length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Column(name = "file_size_kb")
    public BigDecimal getFileSizeKb() {
        return fileSizeKb;
    }

    @Size(max = 50)
    @Column(name = "mime_type", length = 50)
    public String getMimeType() {
        return mimeType;
    }

    @ColumnDefault("1")
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    @ColumnDefault("false")
    @Column(name = "validated")
    public Boolean getValidated() {
        return validated;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "validated_by")
    public User getValidatedBy() {
        return validatedBy;
    }

    @Column(name = "validated_at")
    public Instant getValidatedAt() {
        return validatedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "doc_type_code")
    public DocumentType getDocTypeCode() {
        return docTypeCode;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    @ColumnDefault("0")
    @Column(name = "ratings_count")
    public Integer getRatingsCount() {
        return ratingsCount;
    }

    @ColumnDefault("0")
    @Column(name = "download_count")
    public Integer getDownloadCount() {
        return downloadCount;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
