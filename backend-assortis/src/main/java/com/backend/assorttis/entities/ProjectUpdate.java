package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "project_updates", schema = "public", indexes = {
        @Index(name = "idx_project_updates_project", columnList = "project_id")
})
public class ProjectUpdate {
    private Long id;

    private Project project;

    private User user;

    private String updateText;

    private String attachmentUrl;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @NotNull
    @Column(name = "update_text", nullable = false, length = Integer.MAX_VALUE)
    public String getUpdateText() {
        return updateText;
    }

    @Column(name = "attachment_url", length = Integer.MAX_VALUE)
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
