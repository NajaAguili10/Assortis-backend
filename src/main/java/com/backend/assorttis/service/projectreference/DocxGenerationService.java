package com.backend.assorttis.service.projectreference;

import com.backend.assorttis.dto.projectreference.ProjectReferenceTemplate;
import com.backend.assorttis.enums.ProjectReferenceGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;

@Slf4j
@Service
public class DocxGenerationService {

    public byte[] generate(ProjectReferenceTemplate template, Map<String, String> variables) {
        try (InputStream inputStream = Files.newInputStream(template.getPath());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(inputStream);
            VariablePrepare.prepare(wordPackage);
            wordPackage.getMainDocumentPart().variableReplace(variables);
            wordPackage.save(outputStream);
            return outputStream.toByteArray();
        } catch (Exception exception) {
            log.error("Unable to generate project reference for template={}", template.getPath(), exception);
            throw new ProjectReferenceGenerationException("Unable to generate reference", exception);
        }
    }
}
