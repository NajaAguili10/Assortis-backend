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
@Table(name = "permissions", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "permissions_code_key", columnNames = { "code" })
})
public class Permission {
    private Long id;

    private String code;

    private String module;

    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "code", nullable = false, length = 100)
    public String getCode() {
        return code;
    }

    @Size(max = 50)
    @Column(name = "module", length = 50)
    public String getModule() {
        return module;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

}
