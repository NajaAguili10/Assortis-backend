package com.backend.assorttis.dto.expert;

import lombok.Data;

import java.util.List;

@Data
public class ExpertSearchResponse {
    private List<ExpertDTO> data;
    private PaginationMeta meta;

    @Data
    public static class PaginationMeta {
        private int page;
        private int pageSize;
        private long totalItems;
        private int totalPages;
        private boolean hasNextPage;
        private boolean hasPreviousPage;
    }
}
