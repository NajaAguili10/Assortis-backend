package com.backend.assorttis.dto.expert;

import lombok.Data;

import java.util.List;

@Data
public class ExpertSearchRequest {
    private String firstName;
    private String familyName;
    private String expertId;
    private String keywords;
    private Boolean allWords;
    private Boolean searchOnlineCvs;
    private List<String> sectors;
    private List<String> subSectors;
    private List<String> countries;
    private List<String> regions;
    private List<String> fundingAgencies;
    private List<String> databases;
    private String timeframeExperience;
    private Integer minProjects;
    private String currentlyWorkingIn;
    private List<String> nationality;
    private List<String> education;
    private List<String> languages;
    private String languageLevel;
    private String seniority;
    private String cvLanguage;
}
