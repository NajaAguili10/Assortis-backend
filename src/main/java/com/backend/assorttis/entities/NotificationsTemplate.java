package com.backend.assorttis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "notifications_templates", schema = "public")
public class NotificationsTemplate {
    private Long id;

    private String name;

    private String type;

    private String subject;

    private String content;

    private Map<String, Object> variables;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    @Size(max = 50)
    @Column(name = "type", length = 50)
    public String getType() {
        return type;
    }

    @Size(max = 255)
    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    public String getContent() {
        return content;
    }

    @Column(name = "variables")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getVariables() {
        return variables;
    }

    @ColumnDefault("true")
    @Column(name = "is_active")
    public Boolean getIsActive() {
        return isActive;
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
