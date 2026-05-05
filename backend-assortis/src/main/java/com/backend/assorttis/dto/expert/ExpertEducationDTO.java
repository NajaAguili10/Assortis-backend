package com.backend.assorttis.dto.expert;

import lombok.Data;

@Data
public class ExpertEducationDTO {
    private Long id;
    private String degree;
    private String fieldOfStudy;
    private String institution;
    private String grade;
    private Integer graduationYear;
    private String country;
    private String city;
}
