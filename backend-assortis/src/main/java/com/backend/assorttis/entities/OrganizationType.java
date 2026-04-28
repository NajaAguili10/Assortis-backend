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

@NoArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "organization_types", schema = "public")
public class OrganizationType {
    private String code;

    private String label;

    private String description;

    @Id
    @Size(max = 50)
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    @Size(max = 100)
    @NotNull
    @Column(name = "label", nullable = false, length = 100)
    public String getLabel() {
        return label;
    }

    @Column(name = "description", length = Integer.MAX_VALUE)
    public String getDescription() {
        return description;
    }

}
