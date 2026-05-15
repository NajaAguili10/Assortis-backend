package com.backend.assorttis.dto.project;

import lombok.Data;
import java.time.Instant;
import java.util.Map;

@Data
public class ProjectSavedSearchDTO {
    private Long id;
    private String name;
    private Map<String, Object> payload;
    private Instant createdAt;
}
