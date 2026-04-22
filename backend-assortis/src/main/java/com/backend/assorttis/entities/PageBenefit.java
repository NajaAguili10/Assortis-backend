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
@Table(name = "page_benefits", schema = "public", indexes = {
        @Index(name = "idx_page_benefits_page", columnList = "page_id")
})
public class PageBenefit {
    private Long id;

    private Page page;

    private Map<String, Object> title;

    private Map<String, Object> description;

    private String iconName;

    private String iconBgColor;

    private String iconColor;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "page_id", nullable = false)
    public Page getPage() {
        return page;
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
    @Column(name = "icon_bg_color", length = 50)
    public String getIconBgColor() {
        return iconBgColor;
    }

    @Size(max = 50)
    @Column(name = "icon_color", length = 50)
    public String getIconColor() {
        return iconColor;
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
