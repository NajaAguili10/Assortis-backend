package com.backend.assorttis.config;

import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(2)
public class SectorDataSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;

    @Override
    @Transactional
    public void run(String... args) {
        List<SectorInput> sectors = List.of(
                new SectorInput(1L, "AGRICULTURE & RURAL DEVELOPMENT", "AGRICULTURE ET DEVELOPPEMENT RURAL", "AGRICULTURA & DESARROLLO RURAL "),
                new SectorInput(2L, "CONSTRUCTION & ENGINEERING", "CONSTRUCTION ET INGENIERIE", "CONSTRUCCION / INGENIERIA"),
                new SectorInput(3L, "INFORMATION TECHNOLOGY", "TECHNOLOGIES DE L’INFORMATION", "TECNOLOGIA DE LA INFORMACION"),
                new SectorInput(4L, "SCIENCE & RESEARCH", "SCIENCE & RECHERCHE", "CIENCIA Y INVESTIGACIÓN"),
                new SectorInput(5L, "ECONOMIC DEVELOPMENT", "DEVELOPPEMENT ECONOMIQUE", "DESARROLLO ECONOMICO"),
                new SectorInput(6L, "EDUCATION", "EDUCATION", "EDUCACION"),
                new SectorInput(7L, "ENERGY", "ENERGIE", "ENERGIA"),
                new SectorInput(8L, "ENVIRONMENT", "ENVIRONNEMENT", "MEDIO AMBIENTE"),
                new SectorInput(9L, "HUMANITARIAN AID & RELIEF", "SECOURS ET AIDE HUMANITAIRE", "AYUDA HUMANITARIA & ASISTENCIA"),
                new SectorInput(10L, "FINANCE & BANKING", "FINANCE ET BANQUE", "FINANZAS & OPERACIONES BANCARIAS"),
                new SectorInput(11L, "HEALTH", "SANTE", "SALUD"),
                new SectorInput(12L, "TRADE & INDUSTRY", "COMMERCE ET INDUSTRIE", "COMERCIO & INDUSTRIA"),
                new SectorInput(13L, "PROGRAMME & RESOURCE MANAGEMENT", "GESTION DES PROGRAMMES ET DES RESSOURCES", "PROGRAMA & GESTION DE RECURSOS HUMANOS"),
                new SectorInput(14L, "COMMUNICATION / PUBLIC RELATIONS / INFORMATION SERVICES", "COMMUNICATION / RELATIONS PUBLIQUES / SERVICES D’INFORMATION", "COMMUNICACION / RELACIONES PUBLICAS / SERVICIOS DE INFORMACION"),
                new SectorInput(15L, "PUBLIC ADMINISTRATION", "ADMINISTRATION PUBLIQUE", "ADMINISTRACION PUBLICA"),
                new SectorInput(16L, "SOCIAL SERVICES / SOCIAL SCIENCES / POPULATION", "SERVICES SOCIAUX / SCIENCES SOCIALES / POPULATION", "SERVICIOS SOCIALES / CIENCIAS SOCIALES / POBLACION"),
                new SectorInput(17L, "TELECOMMUNICATIONS", "TELECOMMUNICATIONS", "TELECOMUNICACIONES"),
                new SectorInput(18L, "LAW", "DROIT", "DERECHO"),
                new SectorInput(19L, "TRANSPORT", "TRANSPORTS", "TRANSPORTE"),
                new SectorInput(20L, "URBAN DEVELOPMENT", "DEVELOPPEMENT URBAIN", "DESARROLLO URBANO"),
                new SectorInput(21L, "CONSUMER PROTECTION", "PROTECTION DES CONSOMMATEURS", "PROTECCION DEL CONSUMIDOR")
        );

        for (SectorInput input : sectors) {
            String code = generateCode(input.name_en);
            Optional<Sector> existing = sectorRepository.findByCode(code);

            if (existing.isPresent()) {
                // Mise à jour des informations (garde l'id existant)
                Sector sector = existing.get();
                sector.setName(input.name_en)
                        .setDescription(buildDescription(input));
                sectorRepository.save(sector);
            } else {
                // Création d'un nouveau secteur avec l'id fourni
                Sector sector = new Sector()
                        .setId(input.id)
                        .setCode(code)
                        .setName(input.name_en)
                        .setDescription(buildDescription(input));
                sectorRepository.save(sector);
            }
        }
    }

    /**
     * Génère un code unique à partir du nom anglais :
     * - Supprime les accents
     * - Remplace & par AND
     * - Remplace / et les espaces par _
     * - supprime les caractères non alphanumériques
     * - Passe en majuscules
     */
    private String generateCode(String nameEn) {
        String normalized = Normalizer.normalize(nameEn, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // enlève les accents
        return normalized.toUpperCase()
                .replaceAll("&", "AND")
                .replaceAll("/", "_")
                .replaceAll("[^A-Z0-9_]", "_")
                .replaceAll("_+", "_");
    }

    private String buildDescription(SectorInput input) {
        return String.format("Sector: %s | French: %s | Spanish: %s",
                input.name_en, input.name_fr, input.name_es);
    }

    private record SectorInput(Long id, String name_en, String name_fr, String name_es) {}
}
