package com.backend.assorttis.service;

import com.backend.assorttis.dto.organization.OrganizationTypeDTO;
import com.backend.assorttis.repository.OrganizationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationTypeService {

    private final OrganizationTypeRepository organizationTypeRepository;

    @Transactional(readOnly = true)
    public List<OrganizationTypeDTO> getAllOrganizationTypes() {
        return organizationTypeRepository.findAll().stream().map(ot -> OrganizationTypeDTO.builder()
                .code(ot.getCode())
                .label(ot.getLabel())
                .description(ot.getDescription())
                .build()).collect(Collectors.toList());
    }
}
