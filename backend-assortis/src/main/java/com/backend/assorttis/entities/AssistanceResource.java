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
import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "assistance_resources", schema = "public", indexes = {
        @Index(name = "idx_assistance_resources_type", columnList = "type"),
        @Index(name = "idx_assistance_resources_sector", columnList = "sector_id")
})
public class AssistanceResource {
    private Long id;

    private String title;

    private String description;

    private String type;

    private Sector sector;

    private Language language;

    private BigDecimal fileSizeKb;

    private Integer durationMinutes;

    private LocalDate publishDate;

    private Integer downloadCount;

    private BigDecimal ratingAvg;

    private String author;

    private Map<String, Object> tags;

    private String thumbnailUrl;

    private String fileUrl;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sector_id")
    public Sector getSector() {
        return sector;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "language")
    public Language getLanguage() {
        return language;
    }

    @Column(name = "file_size_kb")
    public BigDecimal getFileSizeKb() {
        return fileSizeKb;
    }

    @Column(name = "duration_minutes")
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    @Column(name = "publish_date")
    public LocalDate getPublishDate() {
        return publishDate;
    }

    @ColumnDefault("0")
    @Column(name = "download_count")
    public Integer getDownloadCount() {
        return downloadCount;
    }

    @Column(name = "rating_avg", precision = 3, scale = 2)
    public BigDecimal getRatingAvg() {
        return ratingAvg;
    }

    @Size(max = 255)
    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    @Column(name = "tags")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getTags() {
        return tags;
    }

    @Column(name = "thumbnail_url", length = Integer.MAX_VALUE)
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Column(name = "file_url", length = Integer.MAX_VALUE)
    public String getFileUrl() {
        return fileUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
