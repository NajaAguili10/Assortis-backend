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
@Table(name = "page_form_fields", schema = "public", indexes = {
        @Index(name = "idx_page_form_fields_page", columnList = "page_id")
})
public class PageFormField {
    private Long id;

    private Page page;

    private String fieldName;

    private String fieldType;

    private Map<String, Object> label;

    private Map<String, Object> placeholder;

    private Boolean required;

    private Map<String, Object> options;

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

    @Size(max = 100)
    @NotNull
    @Column(name = "field_name", nullable = false, length = 100)
    public String getFieldName() {
        return fieldName;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "field_type", nullable = false, length = 50)
    public String getFieldType() {
        return fieldType;
    }

    @NotNull
    @Column(name = "label", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getLabel() {
        return label;
    }

    @Column(name = "placeholder")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getPlaceholder() {
        return placeholder;
    }

    @ColumnDefault("false")
    @Column(name = "required")
    public Boolean getRequired() {
        return required;
    }

    @Column(name = "options")
    @JdbcTypeCode(SqlTypes.JSON)
    public Map<String, Object> getOptions() {
        return options;
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
