package com.backend.assorttis.dto.projectreference;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class ProjectReferenceDto {
    Long projectId;
    String projectTitle;
    String clientName;
    String country;
    String fundingSource;
    String donor;
    String contractValue;
    String startDate;
    String endDate;
    String legalEntity;
    String organization;
    String consortiumMembers;
    String projectDescription;
    String typeAndScopeOfServices;
    String expertsProvided;
    String activitiesCarriedOut;
    Map<String, String> variables;
}
