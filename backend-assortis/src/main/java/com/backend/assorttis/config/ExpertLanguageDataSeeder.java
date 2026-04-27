package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertLanguage;
import com.backend.assorttis.entities.ExpertLanguageId;
import com.backend.assorttis.entities.Language;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertLanguageRepository;
import com.backend.assorttis.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(6)
public class ExpertLanguageDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final LanguageRepository languageRepository;
    private final ExpertLanguageRepository expertLanguageRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        if (expert == null) return;

        String[][] languageData = {
                {"EN", "English", "NATIVE"},
                {"FR", "French", "FLUENT"},
                {"ES", "Spanish", "INTERMEDIATE"},
                {"AR", "Arabic", "BASIC"},
                {"DE", "German", "BASIC"}
        };

        for (String[] data : languageData) {
            String code = data[0];
            String name = data[1];
            String level = data[2];

            Language language = languageRepository.findById(code).orElseGet(() -> {
                Language newLanguage = new Language()
                        .setCode(code)
                        .setName(name)
                        .setNativeName(name)
                        .setIsActive(true);
                return languageRepository.save(newLanguage);
            });

            ExpertLanguageId id = new ExpertLanguageId()
                    .setExpertId(expert.getId())
                    .setLanguageCode(language.getCode());
            
            if (!expertLanguageRepository.existsById(id)) {
                ExpertLanguage expertLanguage = new ExpertLanguage()
                        .setId(id)
                        .setExpert(expert)
                        .setLanguageCode(language)
                        .setProficiency(level);
                expertLanguageRepository.save(expertLanguage);
            }
        }
    }
}
