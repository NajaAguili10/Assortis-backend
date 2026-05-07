package com.backend.assorttis.dto.organization;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CurrentOrganizationUpdateRequest {
    private String name;
    private String type;
    private String legalName;
    private String registrationNumber;
    private Integer yearFounded;
    private String description;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String region;
    private List<String> sectors;
    private List<String> subsectors;
    private List<String> languages;
    private Integer employeesCount;
    private BigDecimal annualTurnover;
    private List<String> services;
}
