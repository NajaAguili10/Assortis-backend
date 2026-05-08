package com.backend.assorttis.dto.projectreference;

import com.backend.assorttis.enums.TemplateFormat;
import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

@Value
@Builder
public class ProjectReferenceTemplate {
    TemplateFormat format;
    String label;
    String fileName;
    Path path;
}
