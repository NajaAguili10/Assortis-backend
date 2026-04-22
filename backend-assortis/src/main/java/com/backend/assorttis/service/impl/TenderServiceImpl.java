// Updated service implementation
package com.backend.assorttis.service.impl;

import com.backend.assorttis.dto.tender.*;
import com.backend.assorttis.entities.Tender;
import com.backend.assorttis.mappers.TenderMapper;
import com.backend.assorttis.repository.TenderOrgInterestRepository;
import com.backend.assorttis.repository.TenderRepository;
import com.backend.assorttis.service.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderRepository tenderRepository;
    private final TenderOrgInterestRepository orgInterestRepository;
    private final TenderMapper tenderMapper;

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponseDTO<TenderListDTO> getActiveTenders(int page, int size, String sector, String country) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate today = LocalDate.now();
        Page<Tender> tenderPage = tenderRepository.findActiveTenders(sector, country, today, pageable);

        List<TenderListDTO> dtos = tenderPage.getContent().stream()
                .map(tenderMapper::toTenderListDTO)
                .collect(Collectors.toList());

        PaginationMetaDTO meta = new PaginationMetaDTO();
        meta.setPage(page);
        meta.setPageSize(size);
        meta.setTotalItems(tenderPage.getTotalElements());
        meta.setTotalPages(tenderPage.getTotalPages());
        meta.setHasNextPage(tenderPage.hasNext());
        meta.setHasPreviousPage(tenderPage.hasPrevious());

        PaginatedResponseDTO<TenderListDTO> response = new PaginatedResponseDTO<>();
        response.setData(dtos);
        response.setMeta(meta);
        return response;
    }

    @Override
    public TenderKPIsDTO getTenderKPIs(Long organizationId) {
        TenderKPIsDTO kpis = new TenderKPIsDTO();

        // Totals
        kpis.setTotalTenders(tenderRepository.count());
        kpis.setActiveTenders(tenderRepository.countByStatusCode("PUBLISHED"));
        kpis.setClosedTenders(tenderRepository.countByStatusCode("CLOSED"));
        kpis.setAwardedTenders(tenderRepository.countByStatusCode("AWARDED"));

        // Average budget
        Double avgBudget = tenderRepository.averageEstimatedBudget();
        if (avgBudget != null) {
            MoneyDTO avgMoney = new MoneyDTO();
            avgMoney.setAmount(BigDecimal.valueOf(avgBudget));
            avgMoney.setCurrency("EUR"); // Adapt if currency varies
            avgMoney.setFormatted(String.format("€ %,.0f", avgBudget));
            kpis.setAverageBudget(avgMoney);
        }

        // Organization-specific data (example)
        kpis.setMySubmissions(8L);
        kpis.setMyPendingSubmissions(3L);
        kpis.setMyInvitations(5L);

        BigDecimal pipeline = orgInterestRepository.sumPipelineValueByOrganization(organizationId);
        if (pipeline != null) {
            MoneyDTO pipelineMoney = new MoneyDTO();
            pipelineMoney.setAmount(pipeline);
            pipelineMoney.setCurrency("EUR");
            pipelineMoney.setFormatted(String.format("€ %,.0f", pipeline));
            kpis.setPipelineValue(pipelineMoney);
        }

        kpis.setAverageProposalsPerTender(2.5); // Example
        kpis.setSuccessRate(0.25); // Example

        return kpis;
    }
}
