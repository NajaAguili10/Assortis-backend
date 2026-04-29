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
@Table(name = "expert_educations", schema = "public")
public class ExpertEducation {
    private Long id;

    private Expert expert;

    private String degree;

    private String fieldOfStudy;

    private String institution;

    private String grade;

    private Country country;

    private City city;

    private Integer graduationYear;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "expert_id")
    public Expert getExpert() {
        return expert;
    }

    @Size(max = 255)
    @Column(name = "degree")
    public String getDegree() {
        return degree;
    }

    @Size(max = 255)
    @Column(name = "field_of_study")
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    @Size(max = 255)
    @Column(name = "institution")
    public String getInstitution() {
        return institution;
    }

    @Size(max = 50)
    @Column(name = "grade", length = 50)
    public String getGrade() {
        return grade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    @Column(name = "graduation_year")
    public Integer getGraduationYear() {
        return graduationYear;
    }
    // Dans ExpertEducation.java, ajouter :
    private EducationDegree degreeType;
    private EducationSubject educationSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_subject_id")
    public EducationSubject getEducationSubject() {
        return educationSubject;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_type_id")
    public EducationDegree getDegreeType() {
        return degreeType;
    }
}
