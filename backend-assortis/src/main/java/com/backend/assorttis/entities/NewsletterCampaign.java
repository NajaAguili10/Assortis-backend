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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "newsletter_campaigns", schema = "public")
public class NewsletterCampaign {
    private Long id;

    private String title;

    private String content;

    private String status;

    private Instant scheduledAt;

    private NewsletterTemplate template;

    private User createdBy;

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

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @Size(max = 50)
    @ColumnDefault("'draft'")
    @Column(name = "status", length = 50)
    public String getStatus() {
        return status;
    }

    @Column(name = "scheduled_at")
    public Instant getScheduledAt() {
        return scheduledAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "template_id")
    public NewsletterTemplate getTemplate() {
        return template;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
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
