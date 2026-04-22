package com.backend.assorttis.dto.tender;

// TenderListDTO.java

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TenderListDTO {
    private Long id;
    private String referenceNumber;
    private String title;
    private String organizationName;
    private String country;
    private LocalDate deadline;
    private Integer daysRemaining;
    private MoneyDTO budget;
    private String status;
    private Integer matchScore;
    private List<String> sectors;       // codes des secteurs
    private List<String> subsectors;    // codes des sous-secteurs
    private LocalDate createdAt;
    private Integer proposalsCount;
    private Integer interestedOrgsCount;
}
