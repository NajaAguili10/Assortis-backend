package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertKeyword;
import com.backend.assorttis.entities.ExpertKeywordId;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(11)
public class ExpertKeywordDataSeeder implements CommandLineRunner {

    private final ExpertKeywordRepository expertKeywordRepository;
    private final ExpertRepository expertRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        
        if (expert != null) {
            List<String> keywords = Arrays.asList("Agile", "Scrum", "Management", "Leadership", "Finance");

            for (String kw : keywords) {
                ExpertKeywordId id = new ExpertKeywordId()
                        .setExpertId(expert.getId())
                        .setKeyword(kw);
                
                if (!expertKeywordRepository.existsById(id)) {
                    ExpertKeyword expertKeyword = new ExpertKeyword()
                            .setId(id)
                            .setExpert(expert);
                    expertKeywordRepository.save(expertKeyword);
                }
            }
        }
    }
}
