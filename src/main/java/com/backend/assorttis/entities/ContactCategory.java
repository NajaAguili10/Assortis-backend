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
@Table(name = "contact_categories", schema = "public")
public class ContactCategory {
    private Long id;

    private Map<String, Object> title;

    private Map<String, Object> description;

    private String iconName;

    private String iconColor;

    private String iconBgColor;

    private String email;

    private Integer displayOrder;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @NotNull
    @Column(name = "title", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getTitle() {
        return title;
    }

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getDescription() {
        return description;
    }

    @Size(max = 100)
    @Column(name = "icon_name", length = 100)
    public String getIconName() {
        return iconName;
    }

    @Size(max = 50)
    @Column(name = "icon_color", length = 50)
    public String getIconColor() {
        return iconColor;
    }

    @Size(max = 50)
    @Column(name = "icon_bg_color", length = 50)
    public String getIconBgColor() {
        return iconBgColor;
    }

    @Size(max = 255)
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @ColumnDefault("0")
    @Column(name = "display_order")
    public Integer getDisplayOrder() {
        return displayOrder;
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

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
