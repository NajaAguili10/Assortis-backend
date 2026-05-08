package com.backend.assorttis.dto.projectreference;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TemplateOptionDto {
    String format;
    String label;
    String fileName;
}
