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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "roles", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "roles_code_key", columnNames = { "code" })
})
public class Role {
    private Long id;

    private String code;

    private String label;

    private String description;

    private Boolean isSystem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Size(max = 100)
    @Column(name = "label", length = 100)
    public String getLabel() {
        return label;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

    @ColumnDefault("false")
    @Column(name = "is_system")
    public Boolean getIsSystem() {
        return isSystem;
    }

}
