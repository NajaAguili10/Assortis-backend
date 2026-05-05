package com.backend.assorttis.dto.expert;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExpertExperienceDTO {
    private Long id;
    private String title;
    private String organization;
    private String donorName;
    private String sectorName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String country;
    private String city;
    private Boolean isCurrent;
}
