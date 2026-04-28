package com.backend.assorttis.entities;

import jakarta.persistence.*;
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
@Table(name = "sectors", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "sectors_code_key", columnNames = { "code" }),
        @UniqueConstraint(name = "sectors_name_key", columnNames = { "name" })
})
public class Sector {
    private Long id;

    private String code;

    private String name;

    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255) // au lieu de 50
    @Column(name = "code", length = 255)
    public String getCode() {
        return code;
    }

    @Size(max = 255)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

}
