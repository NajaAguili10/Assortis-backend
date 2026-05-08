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

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "services", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "services_code_key", columnNames = { "code" })
})
public class Service {
    private Long id;

    private String code;

    private String label;

    private String description;

    private Boolean isActive;

    private Instant createdAt;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 50)
    @NotNull
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    @Size(max = 255)
    @NotNull
    @Column(name = "label", nullable = false)
    public String getLabel() {
        return label;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
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

}
