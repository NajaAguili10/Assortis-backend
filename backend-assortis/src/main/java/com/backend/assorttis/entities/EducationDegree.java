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
@Table(name = "education_degrees", schema = "public")
public class EducationDegree {
    private Long id;
    private String nameEn;
    private String nameFr;
    private String nameEs;
    private String nameRu;
    private String nameAr;
    private Integer displayOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Size(max = 255)
    @Column(name = "name_en")
    public String getNameEn() {
        return nameEn;
    }

    @Size(max = 255)
    @Column(name = "name_fr")
    public String getNameFr() {
        return nameFr;
    }

    @Size(max = 255)
    @Column(name = "name_es")
    public String getNameEs() {
        return nameEs;
    }

    @Size(max = 255)
    @Column(name = "name_ru")
    public String getNameRu() {
        return nameRu;
    }

    @Size(max = 255)
    @Column(name = "name_ar")
    public String getNameAr() {
        return nameAr;
    }

    @ColumnDefault("0")
    @Column(name = "display_order")
    public Integer getDisplayOrder() {
        return displayOrder;
    }
}
