package com.backend.assorttis.dto.training;

public class CourseSectorSummaryDto {

    private Long id;
    private String code;
    private String name;

    public CourseSectorSummaryDto() {
    }

    public CourseSectorSummaryDto(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
