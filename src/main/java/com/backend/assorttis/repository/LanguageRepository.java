package com.backend.assorttis.repository;

import com.backend.assorttis.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LanguageRepository extends JpaRepository<Language, String>, JpaSpecificationExecutor<Language> {
    java.util.Optional<Language> findByNameIgnoreCase(String name);
}
