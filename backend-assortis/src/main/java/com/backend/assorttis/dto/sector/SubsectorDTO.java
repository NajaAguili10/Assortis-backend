package com.backend.assorttis.dto.sector;

import lombok.Data;

@Data
public class SubsectorDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long sectorId;
}
