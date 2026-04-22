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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "templates", schema = "public", indexes = {
        @Index(name = "idx_templates_type", columnList = "type")
})
public class Template {
    private Long id;

    private String name;

    private String description;

    private String type;

    private Map<String, Object> sectors;

    private Instant createdAt;

    private Instant updatedAt;

    private Integer usageCount;

    private Boolean isPublic;

    private BigDecimal fileSizeKb;

    private String fileUrl;

    private String thumbnailUrl;

    private User createdBy;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return type;
    }

    @Column(name = "sectors")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getSectors() {
        return sectors;
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

    @ColumnDefault("0")
    @Column(name = "usage_count")
    public Integer getUsageCount() {
        return usageCount;
    }

    @ColumnDefault("true")
    @Column(name = "is_public")
    public Boolean getIsPublic() {
        return isPublic;
    }

    @Column(name = "file_size_kb")
    public BigDecimal getFileSizeKb() {
        return fileSizeKb;
    }

    @NotNull
    @Column(name = "file_url", nullable = false, length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @Column(name = "thumbnail_url", length = Integer.MAX_VALUE)
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

}
