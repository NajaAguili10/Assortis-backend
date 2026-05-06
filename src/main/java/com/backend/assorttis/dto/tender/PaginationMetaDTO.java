package com.backend.assorttis.dto.tender;

// PaginationMetaDTO.java

import lombok.Data;

@Data
public class PaginationMetaDTO {
    private Integer page;
    private Integer pageSize;
    private Long totalItems;
    private Integer totalPages;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
}
