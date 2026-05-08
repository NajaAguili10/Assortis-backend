package com.backend.assorttis.dto.tender;

// PaginatedResponseDTO.java

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDTO<T> {
    private List<T> data;
    private PaginationMetaDTO meta;
}
