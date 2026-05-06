package com.backend.assorttis.service.projectreference;

import com.backend.assorttis.dto.projectreference.ProjectReferenceTemplate;
import com.backend.assorttis.dto.projectreference.TemplateOptionDto;
import com.backend.assorttis.enums.TemplateFormat;
import com.backend.assorttis.enums.TemplateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonorTemplateRegistry {

    @Value("${project-references.templates-path:./templates}")
    private String templatesPath;

    public List<TemplateOptionDto> listTemplates() {
        return scanTemplates().stream()
                .map(template -> TemplateOptionDto.builder()
                        .format(template.getFormat().name())
                        .label(template.getLabel())
                        .fileName(template.getFileName())
                        .build())
                .toList();
    }

    public ProjectReferenceTemplate getTemplate(String requestedFormat) {
        TemplateFormat format = TemplateFormat.fromRequest(requestedFormat);
        ProjectReferenceTemplate template = scanTemplates().stream()
                .filter(item -> item.getFormat() == format)
                .findFirst()
                .orElseThrow(() -> new TemplateNotFoundException("Template not found"));

        log.info("Selected project reference template format={} file={}", format, template.getPath());
        return template;
    }

    private List<ProjectReferenceTemplate> scanTemplates() {
        Path root = Path.of(templatesPath).toAbsolutePath().normalize();
        if (!Files.isDirectory(root)) {
            log.warn("Project reference templates folder not found: {}", root);
            return List.of();
        }

        try (Stream<Path> files = Files.list(root)) {
            Map<TemplateFormat, ProjectReferenceTemplate> templates = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".docx"))
                    .map(this::toTemplate)
                    .flatMap(Optional::stream)
                    .collect(Collectors.toMap(
                            ProjectReferenceTemplate::getFormat,
                            Function.identity(),
                            (existing, replacement) -> existing
                    ));

            return templates.values().stream()
                    .sorted(Comparator.comparing(template -> template.getFormat().name()))
                    .toList();
        } catch (IOException exception) {
            log.error("Unable to scan project reference templates from {}", root, exception);
            return List.of();
        }
    }

    private Optional<ProjectReferenceTemplate> toTemplate(Path path) {
        String fileName = path.getFileName().toString();
        try {
            TemplateFormat format = TemplateFormat.fromFileName(fileName);
            return Optional.of(ProjectReferenceTemplate.builder()
                    .format(format)
                    .label(format.getLabel())
                    .fileName(fileName)
                    .path(path)
                    .build());
        } catch (TemplateNotFoundException exception) {
            log.warn("Ignoring unrecognized project reference template file={}", fileName);
            return Optional.empty();
        }
    }
}
