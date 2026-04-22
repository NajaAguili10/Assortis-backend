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
@Table(name = "contact_methods", schema = "public")
public class ContactMethod {
    private Long id;

    private String type;

    private String iconName;

    private Map<String, Object> label;

    private String value;

    private String href;

    private Integer displayOrder;

    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 20)
    @NotNull
    @Column(name = "type", nullable = false, length = 20)
    public String getType() {
        return type;
    }

    @Size(max = 100)
    @Column(name = "icon_name", length = 100)
    public String getIconName() {
        return iconName;
    }

    @NotNull
    @Column(name = "label", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getLabel() {
        return label;
    }

    @NotNull
    @Column(name = "value", nullable = false, length = Integer.MAX_VALUE)
    public String getValue() {
        return value;
    }

    @Column(name = "href", length = Integer.MAX_VALUE)
    public String getHref() {
        return href;
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
