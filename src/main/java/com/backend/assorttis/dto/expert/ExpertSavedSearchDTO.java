package com.backend.assorttis.dto.expert;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class ExpertSavedSearchDTO {
    private Long id;
    private String label;
    private Map<String, Object> payload;
    private Instant createdAt;
}
