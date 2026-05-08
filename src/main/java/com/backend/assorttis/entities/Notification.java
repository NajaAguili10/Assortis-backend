package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "notifications", schema = "public", indexes = {
        @Index(name = "idx_notifications_expires_at", columnList = "expires_at")
})
public class Notification {
    private Long id;

    private User user;

    private String type;

    private String content;

    private Boolean isRead;

    private Map<String, Object> metadata;

    private Instant createdAt;

    private Boolean archived;

    private Instant readAt;

    private Instant expiresAt;

    private String actionLabel;

    private String actionLink;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 50)
    @Column(name = "type", length = 50)
    public String getType() {
        return type;
    }

    @Column(name = "content", length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @ColumnDefault("false")
    @Column(name = "is_read")
    public Boolean getIsRead() {
        return isRead;
    }

    @Column(name = "metadata")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    @ColumnDefault("false")
    @Column(name = "archived")
    public Boolean getArchived() {
        return archived;
    }

    @Column(name = "read_at")
    public Instant getReadAt() {
        return readAt;
    }

    @Column(name = "expires_at")
    public Instant getExpiresAt() {
        return expiresAt;
    }

    @Size(max = 100)
    @Column(name = "action_label", length = 100)
    public String getActionLabel() {
        return actionLabel;
    }

    @Column(name = "action_link", length = Integer.MAX_VALUE)
    public String getActionLink() {
        return actionLink;
    }

}
