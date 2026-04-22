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

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "course_modules", schema = "public")
public class CourseModule {
    private Long id;

    private Cours course;

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer orderIndex;

    private String contentType;

    private String contentUrl;

    private Map<String, Object> resources;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id", nullable = false)
    public Cours getCourse() {
        return course;
    }

    @Size(max = 255)
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @Column(name = "duration_minutes")
    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    @NotNull
    @Column(name = "order_index", nullable = false)
    public Integer getOrderIndex() {
        return orderIndex;
    }

    @Size(max = 50)
    @Column(name = "content_type", length = 50)
    public String getContentType() {
        return contentType;
    }

    @Column(name = "content_url", length = Integer.MAX_VALUE)
    public String getContentUrl() {
        return contentUrl;
    }

    @Column(name = "resources")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getResources() {
        return resources;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
