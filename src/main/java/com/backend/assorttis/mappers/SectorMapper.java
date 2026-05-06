package com.backend.assorttis.mappers;

import com.backend.assorttis.dto.sector.SectorDTO;
import com.backend.assorttis.dto.sector.SubsectorDTO;
import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.entities.Subsector;
import org.springframework.stereotype.Component;

@Component
public class SectorMapper {

    public SectorDTO toSectorDTO(Sector sector) {
        if (sector == null) return null;
        SectorDTO dto = new SectorDTO();
        dto.setId(sector.getId());
        dto.setCode(sector.getCode());
        dto.setName(sector.getName());
        dto.setDescription(sector.getDescription());
        return dto;
    }

    public SubsectorDTO toSubsectorDTO(Subsector subsector) {
        if (subsector == null) return null;
        SubsectorDTO dto = new SubsectorDTO();
        dto.setId(subsector.getId());
        dto.setCode(subsector.getCode());
        dto.setName(subsector.getName());
        dto.setDescription(subsector.getDescription());
        if (subsector.getSector() != null) {
            dto.setSectorId(subsector.getSector().getId());
        }
        return dto;
    }
}
