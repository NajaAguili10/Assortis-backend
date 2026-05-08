package com.backend.assorttis.dto.organization;

import com.backend.assorttis.dto.sector.SectorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationSubscriptionSectorDTO {
    private Long organizationId;
    private List<SectorDTO> sectors;
}
