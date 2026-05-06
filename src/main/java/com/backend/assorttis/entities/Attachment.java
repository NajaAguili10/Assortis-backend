package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "attachments", schema = "public", indexes = {
        @Index(name = "idx_attachments_owner", columnList = "owner_type, owner_id")
})
public class Attachment {
    private Long id;

    private String ownerType;

    private Long ownerId;

    private String fileName;

    private String fileUrl;

    private BigDecimal fileSizeKb;

    private String mimeType;

    private User uploadedBy;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "owner_type", nullable = false, length = 50)
    public String getOwnerType() {
        return ownerType;
    }

    @NotNull
    @Column(name = "owner_id", nullable = false)
    public Long getOwnerId() {
        return ownerId;
    }

    @Size(max = 255)
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    @NotNull
    @Column(name = "file_url", nullable = false, length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Column(name = "file_size_kb")
    public BigDecimal getFileSizeKb() {
        return fileSizeKb;
    }

    @Size(max = 100)
    @Column(name = "mime_type", length = 100)
    public String getMimeType() {
        return mimeType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "uploaded_by")
    public User getUploadedBy() {
        return uploadedBy;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
