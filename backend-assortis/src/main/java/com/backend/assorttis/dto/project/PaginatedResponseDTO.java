package com.backend.assorttis.dto.project;


import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDTO<T> {
    private List<T> data;
    private PaginationMetaDTO meta;

    @Data
    public static class PaginationMetaDTO {
        private Integer page;
        private Integer pageSize;
        private Long totalItems;
        private Integer totalPages;
        private Boolean hasNextPage;
        private Boolean hasPreviousPage;
    }
}
