package com.backend.assorttis.service;
// TenderService.java


import com.backend.assorttis.dto.tender.PaginatedResponseDTO;
import com.backend.assorttis.dto.tender.TenderKPIsDTO;
import com.backend.assorttis.dto.tender.TenderListDTO;

public interface TenderService {
    PaginatedResponseDTO<TenderListDTO> getActiveTenders(int page, int size, String sector, String country);
    TenderKPIsDTO getTenderKPIs(Long organizationId); // organisation connectée
}
