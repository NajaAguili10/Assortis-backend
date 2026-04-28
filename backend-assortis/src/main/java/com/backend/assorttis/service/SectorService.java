package com.backend.assorttis.service;

import com.backend.assorttis.dto.sector.SectorDTO;
import com.backend.assorttis.dto.sector.SubsectorDTO;
import com.backend.assorttis.mappers.SectorMapper;
import com.backend.assorttis.repository.SectorRepository;
import com.backend.assorttis.repository.SubsectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final SectorMapper sectorMapper;

    @Transactional(readOnly = true)
    public List<SectorDTO> getAllSectors() {
        return sectorRepository.findAll().stream()
                .map(sectorMapper::toSectorDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubsectorDTO> getAllSubsectors() {
        return subsectorRepository.findAll().stream()
                .map(sectorMapper::toSubsectorDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubsectorDTO> getSubsectorsBySectorId(Long sectorId) {
        return subsectorRepository.findBySectorId(sectorId).stream()
                .map(sectorMapper::toSubsectorDTO)
                .collect(Collectors.toList());
    }
}
