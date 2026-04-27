package com.backend.assorttis.config;

import com.backend.assorttis.entities.Expert;
import com.backend.assorttis.entities.ExpertSkill;
import com.backend.assorttis.entities.ExpertSkillId;
import com.backend.assorttis.entities.Skill;
import com.backend.assorttis.repository.ExpertRepository;
import com.backend.assorttis.repository.ExpertSkillRepository;
import com.backend.assorttis.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(5)
public class ExpertSkillDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final SkillRepository skillRepository;
    private final ExpertSkillRepository expertSkillRepository;
    private final AtomicLong skillIdCounter = new AtomicLong(100);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Expert expert = expertRepository.findAll().stream().findFirst().orElse(null);
        if (expert == null) return;

        List<String> skillNames = Arrays.asList(
                "Project Management", "Stakeholder Engagement", "M&E", "Budget Management"
        );

        for (String name : skillNames) {
            Skill skill = skillRepository.findByName(name).orElseGet(() -> {
                Skill newSkill = new Skill()
                        .setName(name)
                        .setCategory("General")
                        .setDescription(name + " skill");
                newSkill.setId(skillIdCounter.getAndIncrement());
                return skillRepository.save(newSkill);
            });

            ExpertSkillId id = new ExpertSkillId()
                    .setExpertId(expert.getId())
                    .setSkillId(skill.getId());
            
            if (!expertSkillRepository.existsById(id)) {
                ExpertSkill expertSkill = new ExpertSkill()
                        .setId(id)
                        .setExpert(expert)
                        .setSkill(skill)
                        .setLevel("EXPERT");
                expertSkillRepository.save(expertSkill);
            }
        }
    }
}
