package com.backend.assorttis.dto.organization;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrganizationFiltersDataDTO {
    private List<String> type;
    private List<OrganizationDTO.SectorDTO> sectors;
    private List<SubsectorDTO> subSectors;
    private List<RegionDTO> regions;
    private List<CountryDTO> countries;
    private String status;

    @Data
    public static class SubsectorDTO {
        private Long id;
        private String name;
        private String code;
        private String sectorCode;
    }

    @Data
    public static class RegionDTO {
        private Long id;
        private String name;
        private String code;
    }

    @Data
    public static class CountryDTO {
        private Long id;
        private String name;
        private String code;
        private String regionCode;
    }
}
