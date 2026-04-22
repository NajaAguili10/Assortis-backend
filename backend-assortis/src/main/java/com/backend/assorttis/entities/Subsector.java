package com.backend.assorttis.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "subsectors", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "subsectors_code_key", columnNames = { "code" })
})
public class Subsector {
    private Long id;

    private Sector sector;

    private String code;

    private String name;

    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sector_id")
    public Sector getSector() {
        return sector;
    }

    @Size(max = 50)
    @Column(name = "code", length = 50)
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
