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
@Table(name = "skills", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "skills_name_key", columnNames = { "name" })
})
public class Skill {
    private Long id;

    private String name;

    private String category;

    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Size(max = 100)
    @Column(name = "category", length = 100)
    public String getCategory() {
        return category;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

}
