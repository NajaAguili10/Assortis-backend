package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(8)
public class ExpertEducationDataSeeder implements CommandLineRunner {

    private final ExpertRepository expertRepository;
    private final ExpertEducationRepository expertEducationRepository;
    private final EducationDegreeRepository educationDegreeRepository;
    private final EducationSubjectRepository educationSubjectRepository;
    private final CountryRepository countryRepository;
    private final AtomicLong eduIdCounter = new AtomicLong(1000);
    private final AtomicLong subjectIdCounter = new AtomicLong(1000);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (expertEducationRepository.count() > 0) return;

        List<Expert> experts = expertRepository.findAll();
        if (experts.isEmpty()) return;

        // Seed Degrees if empty
        if (educationDegreeRepository.count() == 0) {
            educationDegreeRepository.save(new EducationDegree().setNameEn("PhD").setNameFr("Doctorat").setDisplayOrder(1));
            educationDegreeRepository.save(new EducationDegree().setNameEn("Master's Degree").setNameFr("Master").setDisplayOrder(2));
            educationDegreeRepository.save(new EducationDegree().setNameEn("Bachelor's Degree").setNameFr("Licence").setDisplayOrder(3));
        }

        // Seed Subjects if empty
        if (educationSubjectRepository.count() == 0) {
            educationSubjectRepository.save(new EducationSubject().setId(subjectIdCounter.getAndIncrement()).setNameEn("Economics").setNameFr("Économie"));
            educationSubjectRepository.save(new EducationSubject().setId(subjectIdCounter.getAndIncrement()).setNameEn("Engineering").setNameFr("Ingénierie"));
            educationSubjectRepository.save(new EducationSubject().setId(subjectIdCounter.getAndIncrement()).setNameEn("Public Health").setNameFr("Santé Publique"));
            educationSubjectRepository.save(new EducationSubject().setId(subjectIdCounter.getAndIncrement()).setNameEn("Computer Science").setNameFr("Informatique"));
        }

        EducationDegree master = educationDegreeRepository.findByNameEn("Master's Degree").orElse(null);
        EducationDegree bachelor = educationDegreeRepository.findByNameEn("Bachelor's Degree").orElse(null);

        EducationSubject economics = educationSubjectRepository.findByNameEn("Economics").orElse(null);
        EducationSubject engineering = educationSubjectRepository.findByNameEn("Engineering").orElse(null);
        EducationSubject health = educationSubjectRepository.findByNameEn("Public Health").orElse(null);

        Country usa = countryRepository.findByCode("US").orElse(null);
        Country uk = countryRepository.findByCode("GB").orElse(null);
        Country france = countryRepository.findByCode("FR").orElse(null);

        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);
            
            // Assign a Master's degree
            ExpertEducation edu1 = new ExpertEducation()
                    .setId(eduIdCounter.getAndIncrement())
                    .setExpert(expert)
                    .setDegree("Master of Science")
                    .setDegreeType(master)
                    .setEducationSubject(i % 3 == 0 ? economics : (i % 3 == 1 ? engineering : health))
                    .setInstitution(i % 2 == 0 ? "Harvard University" : "Oxford University")
                    .setFieldOfStudy(i % 3 == 0 ? "International Economics" : (i % 3 == 1 ? "Civil Engineering" : "Global Health"))
                    .setGraduationYear(2010 + (i % 10))
                    .setCountry(i % 2 == 0 ? usa : uk);
            
            expertEducationRepository.save(edu1);

            // Assign a Bachelor's degree for some
            if (i % 2 == 0) {
                ExpertEducation edu2 = new ExpertEducation()
                        .setId(eduIdCounter.getAndIncrement())
                        .setExpert(expert)
                        .setDegree("Bachelor of Arts")
                        .setDegreeType(bachelor)
                        .setEducationSubject(economics)
                        .setInstitution("University of Paris")
                        .setFieldOfStudy("Social Sciences")
                        .setGraduationYear(2006 + (i % 5))
                        .setCountry(france);
                expertEducationRepository.save(edu2);
            }
        }
    }
}
