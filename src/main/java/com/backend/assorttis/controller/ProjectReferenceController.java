package com.backend.assorttis.controller;

import com.backend.assorttis.dto.projectreference.TemplateOptionDto;
import com.backend.assorttis.service.projectreference.ProjectReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/project-references")
@RequiredArgsConstructor
public class ProjectReferenceController {

    private static final String DOCX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private final ProjectReferenceService projectReferenceService;

    @GetMapping("/templates")
    public ResponseEntity<List<TemplateOptionDto>> listTemplates() {
        return ResponseEntity.ok(projectReferenceService.listTemplates());
    }

    @GetMapping("/{projectId}/download")
    public ResponseEntity<byte[]> downloadReference(
            @PathVariable Long projectId,
            @RequestParam String format
    ) {
        ProjectReferenceService.GeneratedProjectReference reference = projectReferenceService.generate(projectId, format);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(DOCX_CONTENT_TYPE))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(reference.getFileName())
                        .build()
                        .toString())
                .body(reference.getContent());
    }
}
