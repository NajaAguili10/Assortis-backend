package com.backend.assorttis.config;

import com.backend.assorttis.entities.*;
import com.backend.assorttis.entities.enums.project.ProjectPriorityEnum;
import com.backend.assorttis.entities.enums.project.ProjectStatus;
import com.backend.assorttis.entities.enums.project.ProjectTypeEnum;
import com.backend.assorttis.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
@Order(5)
public class ProjectDataSeeder implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final CountryRepository countryRepository;
    private final SectorRepository sectorRepository;
    private final CityRepository cityRepository;
    private final DonorRepository donorRepository;
    private final ProjectSubsectorRepository projectSubsectorRepository;
    private final SubsectorRepository subsectorRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectOrganizationRepository projectOrganizationRepository;

    private final AtomicLong countryIdCounter = new AtomicLong(200);
    private final AtomicLong cityIdCounter = new AtomicLong(200);
    private final AtomicLong donorIdCounter = new AtomicLong(200);
    private final AtomicLong sectorIdCounter = new AtomicLong(200);
    private final AtomicLong taskIdCounter = new AtomicLong(1000);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (projectRepository.count() > 5) {
            return;
        }

        Organization assortisTech = organizationRepository.findAll().stream()
                .filter(o -> "Assortis Tech".equals(o.getName()))
                .findFirst()
                .orElseGet(() -> organizationRepository.findAll().stream().findFirst().orElse(null));

        // Projects Data from ProjectsContext.tsx
        
        // 1. EDUCATION Development Project 1
        createProject("PROJ-2024-001", "EDUCATION Hajer Project 1", "Construction and renovation of primary schools in rural communities to improve access to quality education.", "ACTIVE", "HIGH", "INFRASTRUCTURE", "World Bank", "WB", "GRANT", new BigDecimal("2500000"), "USD", "Kenya", "KE", "Nairobi", LocalDate.of(2023, 6, 1), LocalDate.of(2025, 5, 31), "Education", "EDUCATION", assortisTech, Map.of("main", "Improve access to quality education"), Map.of("d1", "10 primary schools renovated"), new String[]{"PRIMARY_EDUCATION", "INFRASTRUCTURE"});

        // 2. HEALTH Development Project 2
        createProject("PROJ-2024-002", "HEALTH Development Project 2", "Comprehensive program to reduce maternal and child mortality through improved healthcare services and community awareness.", "ACTIVE", "URGENT", "DEVELOPMENT", "UNICEF", "UNICEF", "GRANT", new BigDecimal("3200000"), "USD", "Ethiopia", "ET", "Addis Ababa", LocalDate.of(2023, 3, 1), LocalDate.of(2025, 2, 28), "Health", "HEALTH", assortisTech, Map.of("main", "Reduce maternal and child mortality"), Map.of("d1", "Healthcare services improved"), new String[]{"PRIMARY_HEALTHCARE", "MATERNAL_HEALTH"});

        // 3. AGRICULTURE Development Project 3
        createProject("PROJ-2024-003", "AGRICULTURE Development Project 3", "Training program for smallholder farmers on sustainable agricultural practices and climate-smart technologies.", "ACTIVE", "MEDIUM", "CAPACITY_BUILDING", "FAO", "FAO", "GRANT", new BigDecimal("850000"), "USD", "Senegal", "SN", "Dakar", LocalDate.of(2024, 1, 1), LocalDate.of(2025, 12, 31), "Agriculture", "AGRICULTURE", assortisTech, Map.of("main", "Promote sustainable agricultural practices"), Map.of("d1", "Farmer training completed"), new String[]{"CROP_PRODUCTION", "RURAL_DEVELOPMENT"});

        // 4. INFRASTRUCTURE Development Project 4
        createProject("PROJ-2023-042", "INFRASTRUCTURE Development Project 4", "Installation of clean water systems and sanitation facilities in underserved communities.", "ACTIVE", "HIGH", "INFRASTRUCTURE", "African Development Bank", "AfDB", "LOAN", new BigDecimal("4500000"), "USD", "Tanzania", "TZ", "Dodoma", LocalDate.of(2023, 7, 1), LocalDate.of(2025, 6, 30), "Water & Sanitation", "WATER_SANITATION", assortisTech, Map.of("main", "Install clean water systems"), Map.of("d1", "Water systems installed"), new String[]{"WATER_SUPPLY", "SANITATION"});

        // 5. ENVIRONMENT Development Project 5
        createProject("PROJ-2024-015", "ENVIRONMENT Development Project 5", "Development and implementation of climate change adaptation strategies for vulnerable coastal communities.", "PLANNING", "MEDIUM", "RESEARCH", "UNDP", "UNDP", "GRANT", new BigDecimal("1200000"), "USD", "Bangladesh", "BD", "Dhaka", LocalDate.of(2024, 4, 1), LocalDate.of(2026, 3, 31), "Environment", "ENV", assortisTech, Map.of("main", "Develop climate adaptation strategies"), Map.of("d1", "Adaptation strategy report"), new String[]{"CLIMATE_CHANGE", "SUSTAINABLE_DEVELOPMENT"});

        // 6. ENERGY Development Project 6
        createProject("PROJ-2023-028", "ENERGY Development Project 6", "Installation of solar power systems in rural areas to improve energy access and reduce carbon emissions.", "COMPLETED", "MEDIUM", "INFRASTRUCTURE", "African Development Bank", "AfDB", "LOAN", new BigDecimal("5600000"), "USD", "Morocco", "MA", "Rabat", LocalDate.of(2022, 1, 1), LocalDate.of(2024, 1, 31), "Energy", "ENERGY", assortisTech, Map.of("main", "Install solar power systems"), Map.of("d1", "Solar units installed"), new String[]{"RENEWABLE_ENERGY", "SOLAR"});

        // 7. GOVERNANCE Development Project 7
        createProject("PROJ-2024-007", "GOVERNANCE Development Project 7", "Strengthening public institutions and democratic governance frameworks in post-conflict regions.", "ACTIVE", "HIGH", "DEVELOPMENT", "European Commission", "EC", "GRANT", new BigDecimal("1800000"), "EUR", "Mali", "ML", "Bamako", LocalDate.of(2024, 2, 1), LocalDate.of(2026, 1, 31), "Governance", "GOVERNANCE", assortisTech, Map.of("main", "Strengthen public institutions"), Map.of("d1", "Institutional framework report"), new String[]{"PUBLIC_ADMINISTRATION", "JUSTICE_REFORM"});

        // 8. GENDER Development Project 8
        createProject("PROJ-2024-008", "GENDER Development Project 8", "Promoting gender equality and women economic empowerment through entrepreneurship training and access to finance.", "ACTIVE", "MEDIUM", "CAPACITY_BUILDING", "UN Women", "UNW", "GRANT", new BigDecimal("950000"), "USD", "Uganda", "UG", "Kampala", LocalDate.of(2024, 3, 1), LocalDate.of(2025, 8, 31), "Gender", "GENDER", assortisTech, Map.of("main", "Promote gender equality"), Map.of("d1", "Training modules"), new String[]{"WOMENS_EMPOWERMENT", "ECONOMIC_EMPOWERMENT"});

        // 9. DIGITAL Development Project 9
        createProject("PROJ-2024-009", "DIGITAL Development Project 9", "Expanding digital infrastructure and ICT skills to bridge the digital divide in underserved urban districts.", "ACTIVE", "HIGH", "DEVELOPMENT", "USAID", "USAID", "GRANT", new BigDecimal("2100000"), "USD", "India", "IN", "New Delhi", LocalDate.of(2024, 1, 15), LocalDate.of(2025, 7, 14), "Infrastructure", "INFRASTRUCTURE", assortisTech, Map.of("main", "Bridge the digital divide"), Map.of("d1", "Digital infrastructure map"), new String[]{"TELECOMMUNICATIONS", "URBAN_PLANNING"});

        // 10. NUTRITION Development Project 10
        createProject("PROJ-2023-010", "NUTRITION Development Project 10", "Combatting malnutrition and food insecurity through community-based nutrition programs and school feeding initiatives.", "ON_HOLD", "URGENT", "DEVELOPMENT", "World Food Programme", "WFP", "GRANT", new BigDecimal("2800000"), "EUR", "Niger", "NE", "Niamey", LocalDate.of(2023, 9, 1), LocalDate.of(2025, 8, 31), "Health", "HEALTH", assortisTech, Map.of("main", "Combat malnutrition"), Map.of("d1", "Feeding program report"), new String[]{"NUTRITION", "HEALTH_SYSTEMS"});

        // 11. TRANSPORT Development Project 11
        createProject("PROJ-2024-011", "TRANSPORT Development Project 11", "Rehabilitation of rural road networks to improve market access and reduce post-harvest losses for farming communities.", "ACTIVE", "HIGH", "INFRASTRUCTURE", "Inter-American Development Bank", "IDB", "LOAN", new BigDecimal("7200000"), "USD", "Peru", "PE", "Lima", LocalDate.of(2024, 3, 1), LocalDate.of(2027, 2, 28), "Infrastructure", "INFRASTRUCTURE", assortisTech, Map.of("main", "Rehabilitate rural road networks"), Map.of("d1", "Road map completed"), new String[]{"ROADS_TRANSPORT", "BUILDINGS"});

        // 12. FINANCE Development Project 12
        createProject("PROJ-2022-012", "FINANCE Development Project 12", "Improving access to microfinance and financial literacy for small business owners and rural entrepreneurs.", "COMPLETED", "MEDIUM", "CAPACITY_BUILDING", "AFD", "AFD", "GRANT", new BigDecimal("1600000"), "USD", "Ghana", "GH", "Accra", LocalDate.of(2022, 6, 1), LocalDate.of(2024, 5, 31), "Governance", "GOVERNANCE", assortisTech, Map.of("main", "Improve access to microfinance"), Map.of("d1", "Microfinance report"), new String[]{"CIVIL_SOCIETY", "ANTI_CORRUPTION"});

        seedTasks();
    }

    private void seedTasks() {
        if (projectTaskRepository.count() > 0) return;

        // Project 1 tasks
        Project p1 = projectRepository.findAll().stream().filter(p -> "PROJ-2024-001".equals(p.getReferenceCode())).findFirst().orElseThrow();
        createTask(p1, "TSK-001", "Complete site survey for new school building", "Conduct detailed site survey and soil testing for the new primary school construction in Kisumu region.", "IN_PROGRESS", "HIGH", Map.of("names", new String[]{"John Doe", "Jane Smith"}), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 15), Map.of("categories", new String[]{"survey", "infrastructure"}), false);
        createTask(p1, "TSK-002", "Finalize architectural plans", "Complete architectural drawings for 3 school buildings and get approval from Ministry of Education.", "TODO", "URGENT", Map.of("names", new String[]{"Architect Team"}), LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 10), Map.of("categories", new String[]{"design", "planning"}), true);
        createTask(p1, "TSK-006", "Procurement of construction materials", "Source and procure cement, steel, and other construction materials according to approved specifications.", "TODO", "HIGH", Map.of("names", new String[]{"Procurement Team"}), LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 30), Map.of("categories", new String[]{"procurement", "materials"}), false);

        // Project 2 tasks
        Project p2 = projectRepository.findAll().stream().filter(p -> "PROJ-2024-002".equals(p.getReferenceCode())).findFirst().orElseThrow();
        createTask(p2, "TSK-003", "Conduct community health training", "Train 50 community health workers on maternal care and nutrition best practices.", "IN_PROGRESS", "HIGH", Map.of("names", new String[]{"Health Team", "Dr. Amina Hassan"}), LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 20), Map.of("categories", new String[]{"training", "health"}), false);
        createTask(p2, "TSK-004", "Develop training materials", "Create comprehensive training modules on sustainable farming techniques and climate-smart agriculture.", "COMPLETED", "MEDIUM", Map.of("names", new String[]{"Training Department"}), LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28), Map.of("categories", new String[]{"materials", "agriculture"}), false);
        createTask(p2, "TSK-007", "Monthly health monitoring report", "Compile and submit monthly monitoring report on maternal and child health indicators.", "REVIEW", "MEDIUM", Map.of("names", new String[]{"M&E Team"}), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 8), Map.of("categories", new String[]{"monitoring", "reporting"}), false);

        // Project 3 tasks
        Project p3 = projectRepository.findAll().stream().filter(p -> "PROJ-2024-003".equals(p.getReferenceCode())).findFirst().orElseThrow();
        createTask(p3, "TSK-008", "Organize farmer field visits", "Coordinate field visits to demonstration farms for 200 smallholder farmers.", "TODO", "MEDIUM", Map.of("names", new String[]{"Field Officers"}), LocalDate.of(2024, 3, 20), LocalDate.of(2024, 4, 10), Map.of("categories", new String[]{"fieldwork", "training"}), false);

        // Project 4 tasks
        Project p4 = projectRepository.findAll().stream().filter(p -> "PROJ-2023-042".equals(p.getReferenceCode())).findFirst().orElseThrow();
        createTask(p4, "TSK-005", "Install water filtration systems", "Install and test 12 water filtration units in selected communities.", "IN_PROGRESS", "URGENT", Map.of("names", new String[]{"Engineering Team", "Michael Brown"}), LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 25), Map.of("categories", new String[]{"installation", "water"}), false);
        createTask(p4, "TSK-009", "Community awareness campaign", "Launch community awareness campaign on water conservation and sanitation practices.", "IN_PROGRESS", "MEDIUM", Map.of("names", new String[]{"Communications Team", "Community Mobilizers"}), LocalDate.of(2024, 2, 15), LocalDate.of(2024, 3, 31), Map.of("categories", new String[]{"awareness", "community"}), false);

        // Project 5 tasks
        Project p5 = projectRepository.findAll().stream().filter(p -> "PROJ-2024-015".equals(p.getReferenceCode())).findFirst().orElseThrow();
        createTask(p5, "TSK-010", "Baseline vulnerability assessment", "Conduct baseline assessment of climate vulnerabilities in target coastal communities.", "IN_PROGRESS", "HIGH", Map.of("names", new String[]{"Research Team"}), LocalDate.of(2024, 2, 1), LocalDate.of(2024, 3, 15), Map.of("categories", new String[]{"research", "assessment"}), false);
    }

    private void createTask(Project project, String code, String title, String desc, String status, String priority, Map<String, Object> assignedTo, LocalDate start, LocalDate due, Map<String, Object> tags, boolean isMilestone) {
        ProjectTask task = new ProjectTask()
                .setProject(project)
                .setTaskCode(code)
                .setTitle(title)
                .setDescription(desc)
                .setStatus(status)
                .setPriority(priority)
                .setAssignedTo(assignedTo)
                .setStartDate(start)
                .setDueDate(due)
                .setTags(tags)
                .setIsMilestone(isMilestone)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());
        
        task.setId(taskIdCounter.getAndIncrement());
        projectTaskRepository.save(task);
    }

    private void createProject(String code, String title, String desc, String status, String priority, String type,
                               String donorName, String donorShortName, String fundingType, BigDecimal budget, String currency,
                               String countryName, String countryCode, String cityName, LocalDate start, LocalDate end,
                               String sectorName, String sectorCode, Organization org, Map<String, Object> objectives,
                               Map<String, Object> deliverables, String[] subsectorCodes) {

        Country country = countryRepository.findAll().stream()
                .filter(c -> countryCode.equals(c.getCode()))
                .findFirst()
                .orElseGet(() -> {
                    Country c = new Country().setName(countryName).setCode(countryCode);
                    c.setId(countryIdCounter.getAndIncrement());
                    return countryRepository.save(c);
                });

        City city = cityRepository.findAll().stream()
                .filter(c -> cityName.equals(c.getName()))
                .findFirst()
                .orElseGet(() -> {
                    City ct = new City().setName(cityName);
                    ct.setId(cityIdCounter.getAndIncrement());
                    return cityRepository.save(ct);
                });

        Donor donor = donorRepository.findAll().stream()
                .filter(d -> donorName.equals(d.getName()))
                .findFirst()
                .orElseGet(() -> {
                    Donor d = new Donor().setName(donorName).setShortName(donorShortName).setType("MULTILATERAL");
                    d.setId(donorIdCounter.getAndIncrement());
                    return donorRepository.save(d);
                });

        Sector sector = sectorRepository.findByCode(sectorCode)
                .or(() -> sectorRepository.findByName(sectorName))
                .orElseGet(() -> {
                    Sector s = new Sector().setName(sectorName).setCode(sectorCode);
                    s.setId(sectorIdCounter.getAndIncrement());
                    return sectorRepository.save(s);
                });

        Project project = new Project()
                .setReferenceCode(code)
                .setTitle(title)
                .setDescription(desc)
                .setStatus(ProjectStatus.valueOf(status))
                .setPriority(ProjectPriorityEnum.valueOf(priority))
                .setType(ProjectTypeEnum.valueOf(type))
                .setDonor(donor)
                .setFundingType(fundingType)
                .setBudget(budget)
                .setCurrency(currency)
                .setCountry(country)
                .setCity(city)
                .setStartDate(start)
                .setEndDate(end)
                .setMainSector(sector)
                .setValidatedByOrg(org)
                .setIsValidatedByOrg(org != null)
                .setValidatedAt(Instant.now())
                .setObjectives(objectives)
                .setDeliverables(deliverables)
                .setUpdatedAt(Instant.now());

        final Project savedProject = projectRepository.save(project);

        if (subsectorCodes != null) {
            for (String subCode : subsectorCodes) {
                Optional<Subsector> subOpt = subsectorRepository.findByCode(subCode);
                if (subOpt.isPresent()) {
                    ProjectSubsector ps = new ProjectSubsector();
                    ProjectSubsectorId psId = new ProjectSubsectorId()
                            .setProjectId(savedProject.getId())
                            .setSubsectorId(subOpt.get().getId());
                    ps.setId(psId);
                    ps.setProject(savedProject);
                    ps.setSubsector(subOpt.get());
                    projectSubsectorRepository.save(ps);
                }
            }
        }

        if (org != null) {
            ProjectOrganization po = new ProjectOrganization();
            ProjectOrganizationId poId = new ProjectOrganizationId()
                    .setProjectId(savedProject.getId())
                    .setOrganizationId(org.getId());
            po.setId(poId);
            po.setProject(savedProject);
            po.setOrganization(org);
            po.setRole("IMPLEMENTING_PARTNER");
            po.setIsLead(true);
            projectOrganizationRepository.save(po);
        }

        // Also add a secondary organization (Prioritize UNICEF for ACTIVE projects)
        organizationRepository.findAll().stream()
                .filter(o -> !o.getId().equals(org != null ? org.getId() : -1L))
                .filter(o -> "ACTIVE".equalsIgnoreCase(status) ? "UNICEF".equalsIgnoreCase(o.getName()) : true)
                .findFirst()
                .ifPresent(secondaryOrg -> {
                    ProjectOrganization po2 = new ProjectOrganization();
                    ProjectOrganizationId po2Id = new ProjectOrganizationId()
                            .setProjectId(savedProject.getId())
                            .setOrganizationId(secondaryOrg.getId());
                    po2.setId(po2Id);
                    po2.setProject(savedProject);
                    po2.setOrganization(secondaryOrg);
                    po2.setRole("PARTNER");
                    po2.setIsLead(false);
                    projectOrganizationRepository.save(po2);
                });
    }
}
