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
@Table(name = "audit_logs", schema = "public")
public class AuditLog {
    private Long id;

    private User user;

    private String action;

    private String entityType;

    private Long entityId;

    private Map<String, Object> details;

    private String ipAddress;

    private String deviceInfo;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @Size(max = 100)
    @Column(name = "action", length = 100)
    public String getAction() {
        return action;
    }

    @Size(max = 50)
    @Column(name = "entity_type", length = 50)
    public String getEntityType() {
        return entityType;
    }

    @Column(name = "entity_id")
    public Long getEntityId() {
        return entityId;
    }

    @Column(name = "details")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDetails() {
        return details;
    }

    @Size(max = 50)
    @Column(name = "ip_address", length = 50)
    public String getIpAddress() {
        return ipAddress;
    }

    @Column(name = "device_info", length = Integer.MAX_VALUE)
    public String getDeviceInfo() {
        return deviceInfo;
    }

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

}
