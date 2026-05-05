package com.backend.assorttis.service;

import com.backend.assorttis.dto.expert.ExpertDTO;
import com.backend.assorttis.mappers.ExpertMapper;
import com.backend.assorttis.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;

    @Transactional(readOnly = true)
    public List<ExpertDTO> getAllExperts() {
        return expertRepository.findAll().stream()
                .map(expertMapper::toDTO)
                .collect(Collectors.toList());
    }
}
