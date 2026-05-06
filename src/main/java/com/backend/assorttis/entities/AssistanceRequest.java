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
@Table(name = "assistance_requests", schema = "public", indexes = {
        @Index(name = "idx_assistance_requests_sector", columnList = "sector_id"),
        @Index(name = "idx_assistance_requests_status", columnList = "status")
})
public class AssistanceRequest {
    private Long id;

    private String title;

    private String description;

    private String type;

    private Sector sector;

    private String priority;

    private String status;

    private BigDecimal budget;

    private LocalDate requestDate;

    private LocalDate expectedStartDate;

    private Integer durationWeeks;

    private String requesterName;

    private String requesterOrganization;

    private String requesterEmail;

    private Map<String, Object> tags;

    private Instant createdAt;

    private Instant updatedAt;

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

    @Size(max = 20)
    @ColumnDefault("'MEDIUM'")
    @Column(name = "priority", length = 20)
    public String getPriority() {
        return priority;
    }

    @Size(max = 20)
    @ColumnDefault("'PENDING'")
    @Column(name = "status", length = 20)
    public String getStatus() {
        return status;
    }

    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "request_date")
    public LocalDate getRequestDate() {
        return requestDate;
    }

    @Column(name = "expected_start_date")
    public LocalDate getExpectedStartDate() {
        return expectedStartDate;
    }

    @Column(name = "duration_weeks")
    public Integer getDurationWeeks() {
        return durationWeeks;
    }

    @Size(max = 255)
    @Column(name = "requester_name")
    public String getRequesterName() {
        return requesterName;
    }

    @Size(max = 255)
    @Column(name = "requester_organization")
    public String getRequesterOrganization() {
        return requesterOrganization;
    }

    @Size(max = 255)
    @Column(name = "requester_email")
    public String getRequesterEmail() {
        return requesterEmail;
    }

    @Column(name = "tags")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getTags() {
        return tags;
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
