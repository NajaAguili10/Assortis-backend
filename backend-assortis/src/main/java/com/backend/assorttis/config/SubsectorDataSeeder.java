package com.backend.assorttis.config;

import com.backend.assorttis.entities.Sector;
import com.backend.assorttis.entities.Subsector;
import com.backend.assorttis.repository.SectorRepository;
import com.backend.assorttis.repository.SubsectorRepository;
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
@Order(3)
public class SubsectorDataSeeder implements CommandLineRunner {

    private final SectorRepository sectorRepository;
    private final SubsectorRepository subsectorRepository;

    @Override
    @Transactional
    public void run(String... args) {
        List<SubsectorInput> inputs = buildSubsectorList();

        for (SubsectorInput input : inputs) {
            // Récupérer le secteur parent via son ID (mainsector_id)
            Optional<Sector> sectorOpt = sectorRepository.findById(input.mainsectorId);
            if (sectorOpt.isEmpty()) {
                System.err.println("Sector not found for mainsector_id=" + input.mainsectorId + " - skipping subsector: " + input.nameEn);
                continue;
            }
            Sector sector = sectorOpt.get();

            // Vérifier si le sous‑secteur existe déjà par son ID (fourni dans les données)
            Optional<Subsector> existing = subsectorRepository.findById(input.id);
            if (existing.isPresent()) {
                // Mise à jour des informations
                Subsector sub = existing.get();
                sub.setSector(sector);
                sub.setCode(generateCode(input.id));   // ← code basé sur l'ID
                sub.setName(input.nameEn);
                sub.setDescription(buildDescription(input));
                subsectorRepository.save(sub);
            } else {
                // Création d'un nouveau sous‑secteur avec l'ID fourni
                Subsector sub = new Subsector()
                        .setId(input.id)
                        .setSector(sector)
                        .setCode(generateCode(input.id))
                        .setName(input.nameEn)
                        .setDescription(buildDescription(input));
                subsectorRepository.save(sub);
            }
        }
    }


    /**
     * Génère un code unique à partir du nom anglais :
     * - supprime les accents
     * - remplace & par AND, / par _
     * - conserve lettres, chiffres, underscores
     * - passe en majuscules
     * - réduit les underscores multiples
     */
    private String generateCode(Long id) {
        return "SUBSECTOR_" + id;   // Ex: SUBSECTOR_120
    }

    private String buildDescription(SubsectorInput input) {
        return String.format("Subsector: %s | French: %s | Spanish: %s",
                input.nameEn,
                input.nameFr != null ? input.nameFr : "",
                input.nameEs != null ? input.nameEs : "");
    }



    /**
     * Construit la liste des sous‑secteurs à partir des données JSON fournies.
     * Seules les entrées dont mainsector_id est compris entre 1 et 21 sont conservées.
     */
    private List<SubsectorInput> buildSubsectorList() {
        return List.of(
                new SubsectorInput(120L, "Agri-Tech / Digital Agriculture / Smart Farming / Early Warning Systems / Surveillance (Crops)", "Agri-Tech / Agriculture numérique / Agriculture intelligente / Systèmes d’alerte précoce / Surveillance des cultures", "Agri-Tech / Agricultura digital / Agricultura inteligente / Sistemas de alerta temprana / Vigilancia de cultivos", 1L),
                new SubsectorInput(103L, "Agro-industry / Food / Nutrition / Food security", "Agro-industrie / Alimentation / Nutrition / Sécurité alimentaire", "Agroindustria / Alimentación / Nutrición / Seguridad alimentaria", 1L),
                new SubsectorInput(396L, "Biotechnology", "Biotechnologie", "Biotecnología", 1L),
                new SubsectorInput(104L, "Credit / Insurance / Clearing / Economics / Finance", "Crédit / Assurance / Compensations", "Crédito / Seguro / Compensación", 1L),
                new SubsectorInput(107L, "Cultivation / Harvesting / Crop", "Culture / Moisson / Récolte", "Cultivo / Siembra/ Cosecha", 1L),
                new SubsectorInput(109L, "Drying / Processing / Scarifying / Pelletizing", "Séchage / Traitement / Scarifiage / Granulation", "Secado / Procesamiento / Scarifying / Pelletizing", 1L),
                new SubsectorInput(102L, "Farm / Co-operatives / Associations / Community Centres / Community Participation", "Exploitations Agricoles / Coopératives / Associations / Centres Communautaires", "Explotación agrícola / Cooperativa / Asociaciones / Centros Comunitarios", 1L),
                new SubsectorInput(114L, "Fisheries / Aquaculture", "Pêche / Aquaculture", "Pesquerías / Aquacultura", 1L),
                new SubsectorInput(115L, "Fruits & Vegetables", "Fruits et légumes", "Frutas / Verduras / Hortalizas", 1L),
                new SubsectorInput(119L, "Horticulture", "Horticulture", "Horticultura", 1L),
                new SubsectorInput(101L, "Land / Erosion / Soil / Conservation / Sustainable Agriculture / Agroecological approaches", "Terres / Érosion / Sol / Conservation / Agriculture durable / Approches agroécologiques", "Tierras / Erosión / Suelo / Conservación / Agricultura sostenible / Enfoques agroecológicos", 1L),
                new SubsectorInput(419L, "Mapping / Cadastre / Land tenure", "Cartographie / Cadastre / Titularité foncière", "Cartografía / Catastro / Propiedad de la tierra", 1L),
                new SubsectorInput(116L, "Meat & Dairy", "Viande et produits laitiers", "Carne & Lácteos", 1L),
                new SubsectorInput(106L, "Mechanisation / Production", "Mécanisation / Production", "Mecanización / Producción", 1L),
                new SubsectorInput(108L, "Packaging / Storage / Distribution / Marketing / Value Chains / Fair trade", "Emballage / Stockage / Distribution / Marketing / Chaînes de valeur / Commerce équitable", "Embalaje / Almacenamiento / Distribución / Comercialización / Cadenas de valor / Comercio justo", 1L),
                new SubsectorInput(558L, "Pastoralism", "Pastoralisme", "Pastoralismo", 1L),
                new SubsectorInput(112L, "Pest / Disease / Weed", "Parasites / Maladies / Désherbage", "Peste / Enfermedad / Mala hierba / Plaga", 1L),
                new SubsectorInput(418L, "Policy / Planning / Systems / Institutions", "Politiques / Planification / Systèmes / Institutions", "Política / Planificación / Sistemas / Instituciones", 1L),
                new SubsectorInput(397L, "Procurement / Machinery / Equipment / Infrastructure", "Fourniture de machines / Equipements", "Abastecimiento / Maquinaria / Equipamiento", 1L),
                new SubsectorInput(110L, "Seeds / Fertilisers / Agro-Chemicals / Pesticides / Insecticides", "Graines / Engrais / Agrochimie / Pesticides", "Semillas / Fertilizantes / Agroquímicos / Pesticidas", 1L),
                new SubsectorInput(117L, "Semi-arid & arid agriculture", "Agriculture aride et semi-aride", "Agricultura semiárida & árida", 1L),
                new SubsectorInput(118L, "Sub-tropical & tropical agriculture", "Agriculture tropicale et subtropicale", "Agricultura subtropical & tropical", 1L),
                new SubsectorInput(111L, "Testing / Quality Control", "Test / Contrôle de qualité", "Evaluación / Control de calidad", 1L),
                new SubsectorInput(258L, "Veterinary", "Vétérinaire", "Veterinaria", 1L),
                new SubsectorInput(100L, "Water / Drainage / Irrigation / Flood / Well / Water Supply / Hydrology / Water Sanitation / Sanitation networks ", "Eau / Drainage / Irrigation / Inondation / Puits / Approvisionnement en eau / Hydrologie / Assainissement / Réseaux d’assainissement", "Agua / Drenaje / Riego / Inundaciones / Pozos / Hidrología / Redes de saneamiento / Agua potable / Abastecimiento de agua / Aguas residuales", 1L),
                new SubsectorInput(105L, "Zoology / Livestock / Animals / Breeding / Genetics / Cattle / Entomology", "Zoologie / Bétail / Animaux / Génétique / Entomologie / Elevage", "Zoología / Hacienda / Animales / Reproducción / Genética / Ganado / Entomología", 1L),

                new SubsectorInput(122L, "Building / Construction / Civil Works / Demolition", "Immeuble / Construction / Génie civil", "Inmueble / Construcción / Trabajos de Ingeniería Civil", 2L),
                new SubsectorInput(132L, "Buildings / Offices / Houses", "Immeubles / Bureaux / Logements", "Inmuebles / Oficinas / Viviendas", 2L),
                new SubsectorInput(126L, "Dredging & Reclamation / Excavation", "Dragage et assèchement", "Dragado & Desecación", 2L),
                new SubsectorInput(124L, "Equipment / Material / Procurement / Contracting", "Equipement / Matériau / Approvisionnement / Passation de marchés", "Equipamiento / Material / Abastecimiento o Suministro / Contratante", 2L),
                new SubsectorInput(127L, "Finance / Bonds / Insurance / Cost", "Finance / Obligations / Assurance / Coût", "Finanzas / Obligaciones / Seguro / Costo", 2L),
                new SubsectorInput(131L, "Highways / Roads / Bridges / Tunnels", "Autoroutes / Routes", "Autopistas / Carreteras", 2L),
                new SubsectorInput(130L, "Hydraulic Engineering (dams, pipelines, etc)", "Ingénierie hydraulique (barrages, canalisations, etc)", "Ingeniería Hidráulica (embalses, canales u oleoductos, etc.)", 2L),
                new SubsectorInput(129L, "Landscape Engineering", "Plan d'aménagement du paysage", "Ingeniería Paisajista", 2L),
                new SubsectorInput(123L, "Legislation / Permits / Standards / Engineering and construction conflicts", "Législation / Permis / Normes / Conflits en ingénierie et construction", "Legislación / Permisos / Normas / Conflictos de ingeniería y construcción", 2L),
                new SubsectorInput(121L, "Planning / Architecture / Engineering", "Planification / Architecture / Ingénierie", "Planificación / Arquitectura / Ingeniería", 2L),
                new SubsectorInput(424L, "Rehabilitation and maintenance works", "Travaux de rénovation et maintenance", "Rehabilitación y trabajos de mantenimiento", 2L),
                new SubsectorInput(125L, "Site Selection & Land Use", "Choix des sites et  occupation des sols", "Elección del lugar & Utilización de suelos", 2L),
                new SubsectorInput(128L, "Testing / Assessment / Metering / Protection / Signalling / Water Meters", "Test / Evaluation / Métrage / Protection / Signalisation / Compteurs d’eau", " Prueba / Evaluación / Medición / Protección / SeñalizaciónContadores de agua", 2L),
                new SubsectorInput(423L, "Works supervision", "Supervision des travaux", "Supervisión o inspección de trabajos", 2L),

                new SubsectorInput(138L, "Artificial intelligence / Robotics", "Intelligence artificielle", "Inteligencia Artificial", 3L),
                new SubsectorInput(139L, "Automation / Digitalization", "Automatisation", "Automatización", 3L),
                new SubsectorInput(140L, "Computer Networks / ICT / Operations / Maintenance and Support /  Testing / Programming", "Réseaux informatiques / TIC / Exploitation / Maintenance et support / Tests / Programmation", "Redes informáticas / TIC / Operación / Mantenimiento y soporte / Pruebas / Programación", 3L),
                new SubsectorInput(145L, "Computer-Assisted Design (CAD/CAM)", "Conception assistée par ordinateur", "Diseño Asistido de Computadoras (CAD/CAM)", 3L),
                new SubsectorInput(559L, "Cybersecurity / Security Policy / Security of Communications / Data protection", "Cybersécurité / Politique de sécurité / Sécurité des communications / Protection des données", "Ciberseguridad / Política de seguridad / Seguridad de las comunicaciones / Protección de datos", 3L),
                new SubsectorInput(560L, "Data analysis / Data science / IT Program Management", "Analyse de données / Science des données / Gestion de programmes informatiques", "Análisis de datos / Ciencia de datos / Gestión de programas informáticos", 3L),
                new SubsectorInput(134L, "Databases / Warehouses / Data Recovery / Hosting", "Bases de données / Entrepôts / Récupération de données / Hébergement web", "Bases de datos / Almacenes / Recuperación de datos / Alojamiento", 3L),
                new SubsectorInput(142L, "Decision Support Systems", "Système d'information d'aide à la décision", "Sistemas de Ayuda a la Decisión", 3L),
                new SubsectorInput(561L, "Digital technologies / Digital identity / Digital wallets / Electronic signatures / Data interoperability", "Technologies numériques / Identité numérique / Portefeuilles numériques / Signatures électroniques / Interopérabilité des données", "Tecnologías digitales / Identidad digital / Billeteras digitales / Firmas electrónicas / Interoperabilidad de datos", 3L),
                new SubsectorInput(143L, "Geographical Information / GI Systems (GIS) / Navigation Systems / Localisation / Surveillance Systems / Drones", "Information géographique / Systèmes SIG (GIS) / Systèmes de navigation / Localisation / Systèmes de surveillance / Drones", "Información Geográfica / Sistemas de Información Geográfica (SIG) / Sistemas de Navegación / Localización / Sistemas de Vigilancia / Drones", 3L),
                new SubsectorInput(141L, "Hardware / Equipment / Software / Applications", "Equipements informatiques / Logiciels", "Hardware/ Equipamiento / Software", 3L),
                new SubsectorInput(136L, "Information society / Policy", "Société de l'information", "Sociedad de la Información", 3L),
                new SubsectorInput(135L, "Internet / Web-based Platforms / e-Commerce / e-Governance", "Internet / Commerce électronique", "Internet / Comercio electrónico", 3L),
                new SubsectorInput(133L, "Management Information Systems (MIS)", "Systèmes de gestion de l'information", "Sistemas de Administración de la Información", 3L),
                new SubsectorInput(144L, "Teletext", "Télétexte", "Teletexto", 3L),
                new SubsectorInput(137L, "Web Design / Graphics", "Concepteur HTML / Pages web", "Diseño Web", 3L),

                new SubsectorInput(426L, "Chemistry", "Chimie", "Química", 4L),
                new SubsectorInput(427L, "Earth & Space sciences / Planets / Space research", "Sciences de la Terre et de l’espace / Planètes / Recherche spatiale", "Ciencias de la Tierra y del espacio / Planetas / Investigación espacial", 4L),
                new SubsectorInput(428L, "Human Sciences", "Sciences Humaines", "Ciencias Sociales y Humanas", 4L),
                new SubsectorInput(429L, "Information Sciences", "Sciences de l'Information", "Ciencias de la información", 4L),
                new SubsectorInput(430L, "Life and Health Sciences", "Sciences de la Vie et de la Santé", "Ciencias de la Vida y de la Salud", 4L),
                new SubsectorInput(562L, "Other Sciences", "Autres Sciences", "Otras Ciencias", 4L),
                new SubsectorInput(432L, "Physics", "Physique", "Física", 4L),
                new SubsectorInput(556L, "Procurement / Equipment", "Procurement / Equipment", "Procurement / Equipment", 4L),
                new SubsectorInput(431L, "Technology / Innovation / Nanotechnology", "Technologie / Innovation / Nanotechnologie", "Tecnología / Innovación / Nanotecnología", 4L),

                new SubsectorInput(157L, "Business Centres / Incubators / Business Development / Business Plans / Clustering / Technology parks / Innovation Centres", "Centres d’affaires / Incubateurs / Développement des entreprises / Plans d’affaires / Regroupement / Parcs technologiques / Centres d’innovation", "Centros de negocios / Incubadoras / Desarrollo empresarial / Planes de negocio / Agrupaciones / Parques tecnológicos / Centros de innovación", 5L),
                new SubsectorInput(158L, "Co-operatives", "Coopératives", "Cooperativas", 5L),
                new SubsectorInput(150L, "Corporate Governance", "Administration de société", "Administración de Empresas", 5L),
                new SubsectorInput(149L, "Econometrics / Statistics / Income Distribution", "Econométrie / Statistiques / Distribution des revenus", "Econometría / Estadísticas / Redistribución del Ingreso", 5L),
                new SubsectorInput(148L, "Economic Systems / Planning", "Systèmes économiques / Planification", "Sistemas Económicos / Planeamiento", 5L),
                new SubsectorInput(159L, "Employment / Labour", "Emploi / Main d'oeuvre", "Empleo / Trabajo", 5L),
                new SubsectorInput(161L, "Handicraft", "Artisanat", "Artesanato", 5L),
                new SubsectorInput(420L, "Household / Income Generation", "Revenu des ménages / Comptes d'exploitation", "Familia / Generación de ingresos", 5L),
                new SubsectorInput(147L, "Macroeconomics / Crisis", "Macro-économie", "Macroeconomía", 5L),
                new SubsectorInput(160L, "Matching Grants", "Subventions paritaires / Demandes MEA", "Busqueda o Entrega de Becas-subvenciones", 5L),
                new SubsectorInput(154L, "Mergers & Acquisitions / Partnerships / Joint Ventures", "Fusions et acquisitions / Partenariats / Joint Ventures", "Fusiones & Aquisiciones / Asociaciones / Joint Ventures", 5L),
                new SubsectorInput(156L, "Micro-Credit / Microfinance", "Micro-crédit", "Microcrédito", 5L),
                new SubsectorInput(146L, "Microeconomics", "Micro-économie", "Microeconomía", 5L),
                new SubsectorInput(153L, "Privatisation / Public-Private Partnership", "Privatisation / Partenariat public-privé", "Privatización / Asociación Pública-Privada", 5L),
                new SubsectorInput(151L, "Re-engineering (company) / Liquidation", "Restructuration (entreprises)", "Reingeniería (Empresa)", 5L),
                new SubsectorInput(152L, "Restructuring (sector / industry)", "Restructuration / Reconversion (secteur / industrie)", "Reestructuración (sector / industria)", 5L),
                new SubsectorInput(155L, "Small & Medium-Sized Enterprises (SMEs) / Start-ups", "Petites et Moyennes Entreprises (PME) / Start-up", "Pequeñas y Medianas Empresas (PYMES) / Start-ups", 5L),
                new SubsectorInput(162L, "Tourism / Hotels / Restaurants / Catering", "Tourisme", "Turismo", 5L),
                new SubsectorInput(563L, "Women Economic empowerment", "Autonomisation économique des femmes", "Empoderamiento económico de las mujeres", 5L),

                new SubsectorInput(172L, "Adult Education / Non-Formal / Literacy / Language courses", "Éducation des adultes / Formation non formelle / Alphabétisation / Cours de langues", "Educación de adultos / Formación no formal / Alfabetización / Cursos de idiomas", 6L),
                new SubsectorInput(173L, "Continuing", "Formation continue", "Formación continua", 6L),
                new SubsectorInput(164L, "Curriculum", "Programmes scolaires / Cursus", "Currícula", 6L),
                new SubsectorInput(174L, "Distance Education / e-Learning / Education Technology / Exchange / Scholarship", "Enseignement à distance / Technologie de l'éducation / Echanges", "Educación a distancia / Educación Tecnológica / Intercambio", 6L),
                new SubsectorInput(181L, "Facilities / Architecture / Physical Planning / Location", "Installations / Architecture / Planning / Localisation", "Instalaciones / Arquitectura /Planificación Física / Emplazamiento", 6L),
                new SubsectorInput(178L, "Finance / Accounting / Audit", "Financement / Comptabilité", "Finanzas / Contabilidad", 6L),
                new SubsectorInput(170L, "Higher / University", "Supérieur / Université", "Superior / Universidad", 6L),
                new SubsectorInput(179L, "Library / Documentation Centre / AI-Driven Educational Tools", "Bibliothèque / Centre de documentation / Outils éducatifs basés sur l’IA", "Biblioteca / Centro de documentación / Herramientas educativas basadas en IA", 6L),
                new SubsectorInput(180L, "Manpower / Personnel", "Main d'oeuvre / Personnel", "Mano de Obra / Personal", 6L),
                new SubsectorInput(166L, "Marginalised groups / Handicapped / Girls & Women / Inclusive Education", "Groupes marginalisés / Personnes handicapées / Filles et femmes / Éducation inclusive", "Grupos marginados / Personas con discapacidad / Niñas y mujeres / Educación inclusiva", 6L),
                new SubsectorInput(175L, "Performance / Examinations / Testing / Measurement / Inspectorate / Supervision / National Qualification framework (NQF)", "Performance / Examen / Test / Evaluation / Inspection / Supervision", "Resultado / Examen / Prueba / Medida / Inspección / Supervisión", 6L),
                new SubsectorInput(163L, "Policy / Planning / Systems / Institutions / Evaluation / Decentralisation", "Politique / Planification / Systèmes / Institutions / Evaluation / Décentralisation", "Política / Planificación / Sistemas / Instituciones / Evaluación / Descentralización", 6L),
                new SubsectorInput(167L, "Pre-School / Early Childhood", "Ecole maternelle / Petite enfance", "Pre-escolar / Niñez prematura", 6L),
                new SubsectorInput(168L, "Primary", "Primaire", "Primaria", 6L),
                new SubsectorInput(177L, "Procurement / Equipment / Materials / Media / Techniques / Textbooks / Publishing / Radio & Television", "Matériels / Médias / Techniques / Manuels / Edition / Radio et Télévision", "Abastecimiento / Equipamiento / Materiales / Media / Técnicos / Libros de texto / Publicidad / Radio & Televisión", 6L),
                new SubsectorInput(182L, "Research & Development", "Recherche et Développement", "Investigación & Desarrollo", 6L),
                new SubsectorInput(169L, "Secondary", "Secondaire", "Secundaria", 6L),
                new SubsectorInput(176L, "Teachers Training", "Formation des enseignants / Formateurs", "Formación Docente", 6L),
                new SubsectorInput(165L, "Technical / Industrial / Vocational / Professional education", "Formation technique / Formation industrielle / Formation professionnelle", "Técnica / Industrial / Profesional", 6L),
                new SubsectorInput(171L, "Tertiary", "Enseignement 3e degré", "Terciaria- Terciario", 6L),

                new SubsectorInput(187L, "Conservation / Saving / Recovery / Renewable / Efficiency / Energy transition / Sustainable Energy", "Conservation / Économie / Récupération / Énergies renouvelables / Efficacité / Transition énergétique / Énergie durable", "Conservación / Economía / Recuperación / Energías renovables / Eficiencia / Transición energética / Energía sostenible", 7L),
                new SubsectorInput(189L, "Drilling / Production / Distribution / Reserves", "Forage / Production / Distribution / Réserves", "Perforación / Producción / Distribución / Reservas", 7L),
                new SubsectorInput(191L, "Electric / Lighting / Energy systems construction / Electrical power engineering", "Électrique / Éclairage / Construction de systèmes énergétiques / Génie électrique", "Electricidad / Iluminación / Construcción de sistemas de energía / Electrotecnia", 7L),
                new SubsectorInput(184L, "Financials / Pricing / Tariffs", "Budget / Politique des prix / Tarifs", "Finanzas / Presupuesto / Lista de Precios o Tarifas", 7L),
                new SubsectorInput(192L, "Geothermal / Solar / Bio-Mass / Wind / Sea", "Géothermie / Solaire / Eolienne / Marémotrice / Bio-masse", "Geotérmia / Solar / Masa Biológica / Viento / Mar", 7L),
                new SubsectorInput(186L, "HVAC (Heating, ventilation, and air conditioning) / Heating / Co-Generation", "CVC (chauffage, ventilation et climatisation) / Chauffage / Cogénération", "HVAC (calefacción, ventilación y aire acondicionado) / Calefacción / Cogeneración", 7L),
                new SubsectorInput(421L, "Hydroelectric", "Hydroélectrique", "Hidroeléctrico", 7L),
                new SubsectorInput(188L, "Mining / Coal / Lignite / Anthracite", "Secteur minier / Charbon / Lignite / Anthracite", "Minería / Carbón / Lignito / Antracita", 7L),
                new SubsectorInput(193L, "Nuclear / Nuclear safety", "Nucléaire / Sécurité nucléaire", "Nuclear / Seguridad nuclear", 7L),
                new SubsectorInput(190L, "Oil / Gas / Petrol / Fuel / Lubricants", "Pétrole / Gaz / Carburant", "Petróleo / Gas / Gasolina o Bencina", 7L),
                new SubsectorInput(183L, "Planning / Policy", "Planification / Politique", "Planificación / Política", 7L),
                new SubsectorInput(402L, "Procurement / Equipment / Energy Storage / Batteries", "Approvisionnement / Équipement / Stockage d’énergie / Batteries", "Adquisiciones / Equipamiento / Almacenamiento de energía / Baterías", 7L),

                new SubsectorInput(205L, "Bio-diversity / Eco Systems / Biology", "Bio-diversité", "Bio diversidad", 8L),
                new SubsectorInput(195L, "Bio-economy / Green economy / Blue Economy / Circular Economy / Climate Finance", "Bioéconomie / Économie verte / Économie bleue / Économie circulaire / Financement climatique", "Bioeconomía / Economía verde / Economía azul / Economía circular / Financiación climática", 8L),
                new SubsectorInput(200L, "Botany", "Botanique", "Botánica", 8L),
                new SubsectorInput(206L, "Climate / Climate-resilience / Climate Change Adaptation / Decarbonization / Climate Change Mitigation / National Adaptation Plans", "Climat / Résilience climatique / Adaptation au changement climatique / Décarbonisation / Atténuation du changement climatique / Plans nationaux d’adaptation", "Clima / Resiliencia climática / Adaptación al cambio climático / Descarbonización / Mitigación del cambio climático / Planes nacionales de adaptación", 8L),
                new SubsectorInput(202L, "Coastal Zone Management / Marine Biology / Lake / River", "Aménagement zone côtière", "Gestión de Zonas Costeras / Biología Marina", 8L),
                new SubsectorInput(208L, "Drought & Desertification", "Sécheresse et désertification", "Sequía & Desertificación", 8L),
                new SubsectorInput(197L, "Environmental and Social Impact Assessment (ESIA) / Environmental and Economic Assessment (EA )/ Socio-Economic Impact Assessment (SEIA) / Surveys", "Évaluation de l’impact environnemental et social (EIES) / Évaluation environnementale et économique (EEE) / Évaluation de l’impact socio-économique (EISE) / Enquêtes", "Evaluación del impacto ambiental y social (EIAS) / Evaluación ambiental y económica (EA) / Evaluación del impacto socioeconómico (EIEE) / Encuestas", 8L),
                new SubsectorInput(550L, "Environmental Engineering / Science & Technology", "Ingénierie environnementale / Sciences & Technologie", "Ingeniería Ambiental / Ciencia y Tecnología", 8L),
                new SubsectorInput(113L, "Forestry / Deforestation", "Foresterie / Déforestation", "Silvicultura / Deforestación", 8L),
                new SubsectorInput(203L, "Historic & Cultural Heritage / Archaeology", "Patrimoine historique et culturel / Archéologie", "Patrimonio histórico y cultural / Arqueología", 8L),
                new SubsectorInput(525L, "Natural disasters / Prevention", "Catastrophe Naturelle / Prévention", "Desastres Naturales / Prevención", 8L),
                new SubsectorInput(209L, "Natural resources / Ecology / Protection / Conservation", "Ressources naturelles / Écologie / Protection / Conservation", "Recursos naturales / Ecología / Protección / Conservación", 8L),
                new SubsectorInput(194L, "Policy & Strategy (incl. Sustainability)", "Politique et Stratégie", "Política & Estrategia", 8L),
                new SubsectorInput(198L, "Pollution (Air / Water / Soil / Noise / Industrial / Oil) / Clean cooking", "Pollution (air / eau / sol / bruit / industrielle / pétrole) / Cuisson propre", "Contaminación (aire / agua / suelo / ruido / industrial / petróleo) / Cocina limpia", 8L),
                new SubsectorInput(422L, "Procurement", "Biens et équipements", "Abastecimiento", 8L),
                new SubsectorInput(207L, "Protected Areas / Parks / Reserves / Sustainable Tourism / Ecotourism", "Aires protégées / Parcs / Réserves / Tourisme durable / Écotourisme", "Áreas protegidas / Parques / Reservas / Turismo sostenible / Ecoturismo", 8L),
                new SubsectorInput(211L, "Remote Sensing / Early Warning Systems / Surveillance", "Détection à distance / Prévisions / Systèmes d'alerte / Surveillance", "Control / Detección a distancia / Sistemas de Alerta", 8L),
                new SubsectorInput(196L, "Standards", "Normes", "Estándar", 8L),
                new SubsectorInput(210L, "Topography / Geology / Sustainable Land management / Land use planning / Land information systems and applications / Land registration and cadastre", "Topographie / Géologie / Gestion durable des terres / Aménagement du territoire / Systèmes d’information foncière et applications / Enregistrement foncier et cadastre", "Topografía / Geología / Gestión sostenible del territorio / Ordenación del territorio / Sistemas y aplicaciones de la información territorial / Registro de la propiedad y catastro", 8L),
                new SubsectorInput(199L, "Urban Environment", "Environnement urbain", "Medio Ambiente Urbano", 8L),
                new SubsectorInput(201L, "Waste / Toxic / Hazardous / Solid / Clean Technologies / Processing / Recycling", "Déchets / Substances toxiques / Produits dangereux / Produits non biodégradables / Technologies propres", "Desecho / Sustancias Tóxicas / Inflamable / No biodegradable / Tecnologías limpias", 8L),
                new SubsectorInput(204L, "Wildlife", "Faune et flore", "Vida Salvaje", 8L),

                new SubsectorInput(213L, "Food Aid", "Aide alimentaire", "Ayuda alimentaria", 9L),
                new SubsectorInput(217L, "Land Mines / Mine Clearance", "Mines terrestres / Déminage", "Minas Terrestres / Desactivación de minas", 9L),
                new SubsectorInput(218L, "Logistics / Procurement / Equipment / Supply", "Logistique / Approvisionnement / Équipement / Fournitures", "Logística / Adquisiciones / Equipamiento / Suministros", 9L),
                new SubsectorInput(214L, "Medical Aid", "Aide médicale", "Ayuda Médica", 9L),
                new SubsectorInput(552L, "Policy / Planning", "Politique / Planification", "Política / Planificación", 9L),
                new SubsectorInput(216L, "Prevention / Preparedness", "Prévention / Anticipation", "Prevención / Anticipación", 9L),
                new SubsectorInput(212L, "Refugees / Returnees / Displaced", "Réfugiés / Rapatriés / Déplacés", "Refugiados / Repatriados / Desplazado", 9L),
                new SubsectorInput(215L, "Temporary Shelters", "Abris provisoires", "Albergues Provisionales", 9L),

                new SubsectorInput(219L, "Audit / Accountancy / Due Diligence / Inventory", "Audit / Comptabilité", "Auditoría / Contabilidad / Debida Diligencia", 10L),
                new SubsectorInput(235L, "Balance of Payments", "Balance des paiements", "Balanza de Pagos", 10L),
                new SubsectorInput(241L, "Banking system", "Système bancaire", "Operaciones bancarias", 10L),
                new SubsectorInput(221L, "Budgets / Public Finance / Public expenditure", "Budgets / Finances publiques / Dépenses publiques", "Presupuestos / Finanzas públicas / Gastos públicos", 10L),
                new SubsectorInput(232L, "Capital / Reserves / Liquidity / Cash Flow / Liability / Creditors / Treasury", "Capital / Réserves obligatoires / Cash Flow / Liquidités / Dettes / Créancier", "Capital / Reservas / Liquidez / Movimiento de dinero / Responsabilidad / Acreedores", 10L),
                new SubsectorInput(230L, "Co-financing / Fund raising", "Cofinancement", "Cofinanciamiento", 10L),
                new SubsectorInput(242L, "Corporate finance", "Finance d'entreprise", "Finanzas Corporativas", 10L),
                new SubsectorInput(226L, "Credit / Letters of Credit / Loans / Credit Guarantees", "Crédit / Lettre de crédit / Prêt / Garantie au Crédit", "Credito / Cartas de Crédito / Préstamos / Garantías de Crédito", 10L),
                new SubsectorInput(224L, "Currency / Exchange", "Devises / Opérations de change", "Divisa / Cambio", 10L),
                new SubsectorInput(236L, "Debt / Indebtedness", "Dette / Endettement", "Deuda / Endeudamiento", 10L),
                new SubsectorInput(223L, "Financial Institutions", "Institutions financières", "Instituciones Financieras", 10L),
                new SubsectorInput(238L, "Insurance", "Assurance", "Seguro", 10L),
                new SubsectorInput(231L, "Investment / Venture capital / Portfolio / Securities / Bonds / Stocks / Equities / Funds / Digital Trade", "Investissement / Capital-risque / Portefeuille / Titres / Obligations / Actions / Fonds / Commerce numérique", "Inversión / Capital riesgo / Cartera / Valores / Bonos / Acciones / Renta variable / Fondos / Comercio digital", 10L),
                new SubsectorInput(239L, "Leasing", "Crédit-bail (Leasing)", "Leasing", 10L),
                new SubsectorInput(228L, "Market Instruments / Derivatives / Financial Markets / Exchanges", "Instruments du marché / Produits dérivés / Marchés financiers / Marchés boursiers", "Instrumentos de Mercado / Derivados / Mercados Financieros / Intercambio", 10L),
                new SubsectorInput(237L, "Monetary / Fiscal Policy", "Politique fiscale et monétaire", "Política Monetaria / Fiscal", 10L),
                new SubsectorInput(234L, "Money laundering / Fraud / Anticorruption measures", "Blanchiment d’argent / Fraude / Mesures anticorruption", "Blanqueo de dinero / Fraude / Medidas anticorrupción", 10L),
                new SubsectorInput(225L, "Payments / Mobile Banking / E-Banking / FinTech / Digital Payments", "Paiements / Banque mobile / Banque en ligne / FinTech / Paiements numériques", "Pagos / Banca móvil / Banca electrónica / Tecnología financiera / Pagos digitales", 10L),
                new SubsectorInput(240L, "Pensions", "Rente / Pension", "Pensiones", 10L),
                new SubsectorInput(526L, "Policy / Planning / Systems", "Politique / Planification / Système", "Política / Planificación / Sistemas", 10L),
                new SubsectorInput(222L, "Regulations / Crisis", "Règlementation", "Reglamentaciones", 10L),
                new SubsectorInput(227L, "Reporting / Finance Management", "Comptes rendus financiers / Reporting", "Informe", 10L),
                new SubsectorInput(220L, "Risk / Sensitivity / Market analysis / Forecasting / Models / Projection", "Risques / Modèles / Etudes de marché / Prévisions financières / Pronostics", "Riesgo / Sensibilidad / Análisis de Mercado / Previsiones / Modelos / Proyección", 10L),
                new SubsectorInput(229L, "Structural Adjustment", "Ajustements structurels", "Ajustes Estructurales", 10L),
                new SubsectorInput(233L, "Taxation / Collection", "Impôts / Taxes / Recouvrement", "Impuestos / Recaudación", 10L),

                new SubsectorInput(257L, "Bio-Medicine", "Bio-médecine", "Bio-Medicina", 11L),
                new SubsectorInput(259L, "Diseases / Vaccination / Epidemic / Prevention / Risks", "Maladies / Vaccination / Epidémie", "Enfermedades / Vacunación / Epidemia", 11L),
                new SubsectorInput(244L, "Economics & Finances / Health Insurance", "Economie et finances", "Economía & Finanzas", 11L),
                new SubsectorInput(564L, "Elder people / Geriatrics", "Personnes âgées / Gériatrie", "Personas mayores / Geriatría", 11L),
                new SubsectorInput(260L, "Genetics", "Génétique", "Genética", 11L),
                new SubsectorInput(261L, "Health Care Buildings / Hospital", "Dispensaires / Hôpitaux", "Dispensario / Hospital", 11L),
                new SubsectorInput(262L, "HIV / AIDS / STD", "VIH / Sida / MST", "VIH / SIDA / EST", 11L),
                new SubsectorInput(253L, "Maternal-Child health / Maternity / Family planning / Reproductive health / Nursing", "Médecine prénatale / Maternité / Infirmerie", "Cuidados pre-natales / Lactancia / Maternidad", 11L),
                new SubsectorInput(254L, "Mental Health / Disabled / Psychology", "Santé mentale / Handicapés", "Salud Mental / Minusválido", 11L),
                new SubsectorInput(255L, "Nutrition", "Nutrition", "Nutrición", 11L),
                new SubsectorInput(252L, "Occupational Health", "Médecine du travail", "Medicina de trabajo", 11L),
                new SubsectorInput(401L, "Patients' rights / Protection / Medical Evacuation", "Droits des patients / Protection / Évacuation médicale", "Derechos de los pacientes / Protección / Evacuación médica", 11L),
                new SubsectorInput(245L, "Personnel / Staff / Human Resources", "Main d'oeuvre", "Mano de obra", 11L),
                new SubsectorInput(249L, "Pharmaceuticals / Drugs / Blood", "Produits pharmaceutiques / Médicaments", "Farmacéutico / Drogas o medicamentos", 11L),
                new SubsectorInput(243L, "Policy / Planning / Systems / Organisation / Administration / Management / Systems / e-Health", "Politiques publiques / Planification / Systèmes / Organisation / Administration / Gestion", "Política / Planificación / Sistemas / Organización / Administración / Gestión o Dirección", 11L),
                new SubsectorInput(251L, "Primary Health Care", "Soins de santé primaires", "Cuidados de salud primarios", 11L),
                new SubsectorInput(246L, "Private Health Care Systems / Infrastructure", "Système de santé privé / Infrastructures", "Sistemas de atención de la Salud Privada / Infraestructura", 11L),
                new SubsectorInput(248L, "Procurement / Logistics / Equipment / Supplies / Distribution / Delivery", "Biens d'equipement / Logistique / Approvisionnement / Distribution / Livraison", "Abastecimiento / Logístico / Equipamiento / Proveedor / Distribution / Reparto", 11L),
                new SubsectorInput(247L, "Public Health Care Systems / Infrastructure / Districts", "Système de santé public / Infrastructures / Districts", "Sistemas de atención de la Salud Pública / Infraestructura / Distri", 11L),
                new SubsectorInput(551L, "Research", "Recherche", "Investicación", 11L),
                new SubsectorInput(256L, "Safety / Liability", "Sécurité / Responsabilité médicale", "Seguridad / Responsabilidad médica", 11L),
                new SubsectorInput(565L, "Telemedicine / Telehealth / Health Technology & Innovation", "Télémédecine / Télésanté / Technologie et innovation en santé", "Telemedicina / Telesalud / Tecnología e innovación en salud", 11L),
                new SubsectorInput(527L, "Water, sanitation and hygiene (WASH) / Sanitation / Hygiene", "Eau, assainissement et hygiène (WASH) / Assainissement / Hygiène", "Agua, saneamiento e higiene (WASH) / Saneamiento / Higiene", 11L),

                new SubsectorInput(265L, "Chambers of Commerce", "Chambres de Commerce", "Cámaras de Comercio", 12L),
                new SubsectorInput(266L, "Commodities / Raw Materials", "Produits et marchandises / Matières premières", "Artículo de consumo / Materias primas", 12L),
                new SubsectorInput(270L, "Export / Competition", "Exportation", "Exportación", 12L),
                new SubsectorInput(425L, "Foreign Direct Investments / Investment promotion", "Investissements étrangers", "Inversiones Extrangeras Directas", 12L),
                new SubsectorInput(277L, "Free Trade Zones / Industrial Parks / Estates", "Zones franches / Zones industrielles / Patrimoine immobilier", "Zonas de Libre Comercio / Parques Industriales / Bienes Raíces / Inmuebles", 12L),
                new SubsectorInput(566L, "Heavy engineering / Shipbuilding", "Ingénierie lourde / Construction navale", "Ingeniería pesada / Construcción naval", 12L),
                new SubsectorInput(269L, "Import", "Importation", "Importación", 12L),
                new SubsectorInput(278L, "Industry / Industrial Products", "Industrie / Produits industriels", "Industria / Productos Industriales", 12L),
                new SubsectorInput(271L, "Licencing / Patents / Trademarks", "Contrats de licence / Brevets / Marques commerciales", "Licencias / Patentes / Marca registrada", 12L),
                new SubsectorInput(274L, "Logistics / Packaging", "Logistique", "Logística", 12L),
                new SubsectorInput(268L, "Market Analysis", "Etude de marché / Suivi de marché", "Análisis de Mercado / Observación del mercado", 12L),
                new SubsectorInput(267L, "Marketing / Direct Marketing / Branding", "Marketing / Marketing direct / Image de marque", "Marketing / Marketing directo / Imagen de marca", 12L),
                new SubsectorInput(532L, "Policy / Planning", "Politique / Planification", "Política / Planificación", 12L),
                new SubsectorInput(272L, "Pricing", "Tarification", "Precios", 12L),
                new SubsectorInput(275L, "Procurement / Purchasing & Supply / Sales / Machinery / Stationery / Furniture", "Biens / Achats et fournitures / Ventes", "Abastecimiento/ Compra y Suministros / Ventas", 12L),
                new SubsectorInput(264L, "Promotion / Aids / Facilitation", "Promotion / Aides / Facilitation", "Promoción / Ayuda / Facilidad", 12L),
                new SubsectorInput(276L, "Retail Management", "Gestion du marché de détail", "Venta al por menor / Comercio Minorista", 12L),
                new SubsectorInput(273L, "Sponsorship", "Parrainage (Sponsoring)", "Patrocinio", 12L),
                new SubsectorInput(263L, "Tariff / Barriers / Contracts / Terms / Restrictions", "Tarifs / Barrières douanières / Contrats / Conditions générales / Limitations", "Tarifas / Barreras / Contratos / Términos / Restricciones", 12L),
                new SubsectorInput(398L, "Trade / Global trade", "Commerce global", "Comercio Global", 12L),

                new SubsectorInput(280L, "Co-ordination / Aid co-ordination", "Coordination", "Coordinación", 13L),
                new SubsectorInput(286L, "Cost / Benefits Analysis / Project Budgeting", "Analyse coût-bénéfices", "Costos / Análisis de Beneficios", 13L),
                new SubsectorInput(279L, "Country Programming / Programme Development / Roadmap", "Programmation nationale / Programmes de développement", "Planificación Nacional / Programa de Desarrollo", 13L),
                new SubsectorInput(567L, "Data Collection / Data analysis", "Collecte de données / Analyse de données", "Recogida de datos / Análisis de datos", 13L),
                new SubsectorInput(554L, "Identification / Needs Analysis / Formulation / Feasibility Study", "Identification / Etude des besoins / Formulation / Etude de faisabilité", "Identificación / Análisis de las necesitades / Formulación / Estudio de factibilidad", 13L),
                new SubsectorInput(285L, "Management Systems & Techniques / SCRUM / AGILE", "Systèmes et techniques de gestion / SCRUM / AGILE", "Sistemas y técnicas de gestión / SCRUM / AGILE", 13L),
                new SubsectorInput(281L, "Monitoring & Evaluation & Assessment / (impact) Studies / Project Audit / Survey", "Contrôle / Evaluation / Estimation / Etudes d'impact", "Monitoreo o Control / Evaluación / Previsión o valoración / Estudios (de impacto)", 13L),
                new SubsectorInput(284L, "Performance Appraisal", "Evaluation des performances", "Evaluación de Resultados", 13L),
                new SubsectorInput(282L, "Personnel / Human Resources Management", "Personnel / Gestion des ressources humaines", "Personal / Gestión de Recursos Humanos", 13L),
                new SubsectorInput(555L, "Procurement / Tender evaluation", "Passation des marchés / évaluation des offres", "Licitación / Evaluación de ofertas", 13L),
                new SubsectorInput(289L, "Project Cycle Management / Project implementation", "Cycle de gestion des projets", "Gestión del Ciclo del Proyecto", 13L),
                new SubsectorInput(288L, "Secretarial & Administrative Support", "Soutien administratif / Secrétariat", "Apoyo Administrativo y de Secretaría", 13L),
                new SubsectorInput(568L, "Sustainable development", "Développement durable", "Desarrollo sostenible", 13L),
                new SubsectorInput(287L, "Total Quality Management (TQM) / Quality Control", "Gestion intégrale de la qualité / Contrôle qualité", "Gestión Integral de la Calidad / Control de Calidad", 13L),
                new SubsectorInput(283L, "Training / Capacity building / Documentation", "Formation / Renforcement des capacités / Documentation", "Capacitación / Fortalecimiento de capacidades / Documentación", 13L),

                new SubsectorInput(301L, "Advertising / Information Campaign / Awareness-raising Campaign", "Publicité / Campagne d'information", "Publicidad / Campañas de Información", 14L),
                new SubsectorInput(290L, "Communications", "Communication", "Communicaciones", 14L),
                new SubsectorInput(296L, "Conferences / Events / Seminars", "Conférences / Manifestations / Séminaires / Formation", "Conferencias / Eventos / Seminarios / Formaciones", 14L),
                new SubsectorInput(569L, "Equipment / Supply", "Équipement / Approvisionnement", "Equipamiento / Suministro", 14L),
                new SubsectorInput(291L, "Information Management / Knowledge Sharing", "Gestion de l'information", "Gestión de la Información", 14L),
                new SubsectorInput(294L, "Journalism / Newspapers", "Journalisme / Journaux", "Periodismo / Periódicos", 14L),
                new SubsectorInput(300L, "Marketing", "Marketing", "Marketing", 14L),
                new SubsectorInput(299L, "Multi media / Radio / Television / Film / Photography / Video", "Multimédia / Radio / Télévision / Cinéma /Photographie / Vidéo", "Multimedia / Radio / Televisión / Cine / Fotografía / Video", 14L),
                new SubsectorInput(298L, "Printing / Publishing", "Impression / Publication", "Impresión / Publicación", 14L),
                new SubsectorInput(292L, "Public Affairs / Public Relations", "Affaires publiques / Relations publiques", "Asuntos Públicos / Relaciones Públicas", 14L),
                new SubsectorInput(570L, "SMM / Social Media / Internet", "SMM / Médias sociaux / Internet", "SMM / Redes sociales / Internet", 14L),
                new SubsectorInput(293L, "Strategy & Policy", "Stratégie et politique", "Estrategia & Política", 14L),
                new SubsectorInput(523L, "Translation / Interpretation", "Traduction / Interprétation", "Traducción / Interpretación", 14L),
                new SubsectorInput(297L, "Writing & Editing / Press Release / Speech", "Ecriture et édition / Communiqué de presse / Discours", "Escritura & Edición / Información de Prensa / Discurso", 14L),

                new SubsectorInput(303L, "Administrative Agencies", "Institutions publiques / Organismes publics", "Agencias Administrativas", 15L),
                new SubsectorInput(305L, "Budget / Budget Support / Public Investment / Finances / Debt", "Budget / Investissement public / Finances / Dette", "Presupuesto / Inversión Pública o del Estado / Finanzas / Deuda", 15L),
                new SubsectorInput(309L, "Customs / Excise", "Douanes / Taxes", "Aduana / Impuesto", 15L),
                new SubsectorInput(319L, "Elections / Polling / Voters", "Elections / Vote", "Elecciones / Votación", 15L),
                new SubsectorInput(571L, "Equipment / Supply / Weapons", "Équipement / Fournitures / Armes", "Equipamiento / Suministros / Armas", 15L),
                new SubsectorInput(524L, "Fight against drugs", "Lutte contre le trafic de drogue", "Lucha contra las drogas", 15L),
                new SubsectorInput(572L, "Fire fighting system", "Système de lutte contre l'incendie", "Sistema contra incendios", 15L),
                new SubsectorInput(314L, "Foreign Affairs / Diplomacy", "Affaires étrangères / Diplomatie", "Asuntos Exteriores / Diplomacia", 15L),
                new SubsectorInput(315L, "Governance", "Bonne gouvernance", "Gobierno", 15L),
                new SubsectorInput(312L, "Justice and Home Affairs / Asylum / Immigration / Borders / Passport / Biometrics / ID / Visa / Public Security Risk Mitigation", "Justice et affaires intérieures / Asile / Immigration / Frontières / Passeport / Biométrie / Carte d’identité / Visa / Atténuation des risques de sécurité publique", "Justicia y asuntos internos / Asilo / Inmigración / Fronteras / Pasaporte / Biometría / Documento de identidad DNI / Visado / Mitigación de riesgos de seguridad pública", 15L),
                new SubsectorInput(310L, "Manpower / Recruitment / HR / Grading", "Main d'oeuvre / Ressources humaines / Recrutement / Grille hiérarchique", "Mano de Obra / Contratación / Jerarquía", 15L),
                new SubsectorInput(316L, "Parliament / Political Parties", "Parlement", "Parlamento", 15L),
                new SubsectorInput(302L, "Planning / Policy / Systems", "Politique / Systèmes / Plans", "Planificación / Política / Sistemas", 15L),
                new SubsectorInput(311L, "Police / Defense / Interior / Security / Prison / Public safety", "Police / Défense / Intérieur / Sécurité / Prison / Sécurité publique", "Policía / Defensa / Interior / Seguridad / Prisiones / Seguridad pública", 15L),
                new SubsectorInput(528L, "Public Procurement", "Commande Publique", "Contratación pública", 15L),
                new SubsectorInput(320L, "Public utilities", "Services Publics", "Utilidades públicas", 15L),
                new SubsectorInput(304L, "Reform / Institutional strengthening", "Réforme", "Reforma", 15L),
                new SubsectorInput(306L, "Regional / Municipal / Local Authorities / Decentralisation", "Régionale / Municipale / Collectivités locales / Décentralisation", "Regional / Municipal / Autoridades Locales / Descentralización", 15L),
                new SubsectorInput(307L, "Regional Country Co-operation / European Integration", "Coopérations régionales dans le monde / Intégration européenne", "Cooperación inter-regional / Integración Europea", 15L),
                new SubsectorInput(308L, "Regional Funds", "Fonds régionaux", "Fondos Regionales", 15L),
                new SubsectorInput(313L, "State Enterprises", "Entreprises d'Etat", "Empresas del Estado", 15L),
                new SubsectorInput(529L, "Terrorism / Corruption / Fight against human traffic", "Terrorisme / Corruption / Lutte contre le traffic d'êtres humains", "Terrorismo / Corrupción / Lucha contra el tráfico de seres humanos", 15L),
                new SubsectorInput(318L, "Twinning", "Jumelages", "Emparejamiento", 15L),

                new SubsectorInput(327L, "Anthropology / Tribal / Ethnic Groups", "Anthropologie / Ethnies", "Antropología / Tribal / Grupos Etnicos", 16L),
                new SubsectorInput(332L, "Classification / Norms / Standards", "Classification / Normes / Standards", "Clasificación / Normas / Standards", 16L),
                new SubsectorInput(333L, "Conflict / Post conflict / Conflict prevention / Political crises / Mediation", "Conflit / Post-conflit / Prévention des conflits / Crises politiques / Médiation", "Conflicto / Postconflicto / Prevención de conflictos / Crisis políticas / Mediación", 16L),
                new SubsectorInput(334L, "Culture / Religion / Sport / Arts", "Culture", "Cultura / Religión / Deporte / Arte", 16L),
                new SubsectorInput(336L, "Democracy / Human Rights", "Démocratie", "Democracia / Derechos Humanos", 16L),
                new SubsectorInput(323L, "Family Planning", "Planning familial", "Planificación Familiar", 16L),
                new SubsectorInput(324L, "Gender / Women / Equal Opportunities / Discrimination / Re-integration", "Femmes / Egalité des chances / Discrimination / Réinsertion", "Género / Mujeres / Igualdad de oportunidades / Discriminación / Reintegración", 16L),
                new SubsectorInput(328L, "Migration / Settlement / Resettlement / Border Crossing", "Migration / Implantation / Réinsertion / Transfert de populations / Migrations frontalières", "Migración / Asentamiento o colonización / Reinserción / Migración fronteriza", 16L),
                new SubsectorInput(326L, "Pastoral Groups / Nomads", "Populations nomades", "Población Pastoril / Nómadas", 16L),
                new SubsectorInput(321L, "Policy / Planning / Systems / Reform / Poverty Alleviation", "Politique / Planification / Systèmes / Réforme / Lutte contre la pauvreté", "Política / Planificación / Sistemas / Reforma / Disminución de la Pobreza", 16L),
                new SubsectorInput(330L, "Procurement / Equipment / Vehicles / Supplies / Facilities / Rent", "Biens / Equipements / Véhicules / Fournitures / Installations", "Equipamiento / Vehículos / Abastecimiento / Instalaciones", 16L),
                new SubsectorInput(329L, "Self-help / Grass-roots", "Organisations de base / Initiatives locales", "Auto-Ayuda / La Base", 16L),
                new SubsectorInput(337L, "Social Security / Pension / Protection", "Sécurité sociale / Retraite", "Seguridad Social / Pensiones / Protección Social", 16L),
                new SubsectorInput(322L, "Statistics / Demography / Population / Census / Opinion Polling / Social Impact Studies", "Statistiques / Démographie / Population / Recensement / Sondages / Études d’impact social", "Estadísticas / Demografía / Población / Censo / Encuestas / Estudios de impacto social", 16L),
                new SubsectorInput(335L, "Volunteering / NGO / CBO / Trade Unions / Civil Society", "ONG / Société civile", "Voluntariado / ONG / Organizciones Comunales / Sindicatos / Sociedad Civil", 16L),
                new SubsectorInput(325L, "Vulnerable Groups / Children / Minorities / Human traffic / Exploitation / Social Inclusion / Orphans / Foster family / Juvenile Justice", "Groupes vulnérables / Enfants / Minorités / Trafic humain / Exploitation / Inclusion sociale / Orphelins / Famille d’accueil / Justice des mineurs", "Grupos vulnerables / Infancia / Minorías / Tráfico de seres humanos / Explotación / Inclusión social / Huérfanos / Familia de acogida / Justicia de menores", 16L),
                new SubsectorInput(331L, "Youth / Youth engagement / Youth empowerment", "Jeunesse / Engagement des jeunes / Autonomisation des jeunes", "Juventud / Compromiso de los jóvenes / Capacitación de los jóvenes", 16L),

                new SubsectorInput(557L, "Mobile systems / Services / Equipment / Satellites / Earth Observation", "Systèmes mobiles / Services / Équipement / Satellites / Observation de la Terre", "Sistemas móviles / Servicios / Equipamiento / Satélites / Observación de la Tierra", 17L),
                new SubsectorInput(530L, "Policy / Planning / Teletext", "Politique / Planification / Télétex", "Política / Planificación / Teletexto", 17L),
                new SubsectorInput(339L, "Post / Courier service", "Services postaux", "Servicios postales /Correo", 17L),
                new SubsectorInput(399L, "Procurement / Equipment / Systems", "Biens / Systèmes / Equipements", "Abastecimiento / Equipamiento /Sistemas", 17L),
                new SubsectorInput(338L, "Tariffs", "Tarifs", "Tarifas", 17L),

                new SubsectorInput(347L, "Administrative Law", "Droit administratif", "Derecho Administrativo", 18L),
                new SubsectorInput(553L, "Anti-corruption", "Anti-corruption", "Lucha contra la corrupción", 18L),
                new SubsectorInput(342L, "Bankruptcy / Creditors Rights / Insolvency", "Faillite / Droits des créanciers / Insolvabilité", "Bancarrota / Insolvencia / Derechos del Acreedor", 18L),
                new SubsectorInput(349L, "Civil law / Public law / Election law / Political Party Law", "Droit civil", "Derecho Civil", 18L),
                new SubsectorInput(350L, "Commercial / Trade / Contract / Corporate / Industrial / Economic / Competition Law", "Droit commercial / Commerce / Contrats / Sociétés / Industriel / Sciences économiques / Concurrence", "Comercial / Contratos / Empresarial / Industrial / Económico / Derecho de la Competencia", 18L),
                new SubsectorInput(365L, "Computer / e-Commerce / Internet / Forensic / Cyber Law", "Informatique / Commerce électronique / Internet / Médecine légale / Cyberdroit", "Informática / e-Commerce / Internet / Forense / Ciberderecho", 18L),
                new SubsectorInput(351L, "Constitutional Law", "Droit constitutionnel", "Derecho Constitutional", 18L),
                new SubsectorInput(345L, "Courts / Tribunals / Law Enforcement / Justice / Bailiff", "Cours / Tribunaux / Application de la loi / Justice / Huissier", "Cortes / Tribunales / Aplicación de la ley / Justicia (alguacil)", 18L),
                new SubsectorInput(352L, "Criminal Law / Violence / Crime", "Droit pénal / Violence / Criminalité", "Derecho penal / Violencia / Criminalidad", 18L),
                new SubsectorInput(354L, "Environmental / Energy Law", "Environnement / Code de l'énergie", "Medio Ambiente / Derecho Energético", 18L),
                new SubsectorInput(357L, "Financial / Banking / Investment Law", "Finance / Banque / Code des investissements", "Derecho Financiero / Bancario / De Inversiones", 18L),
                new SubsectorInput(356L, "Governmental Law", "Loi nationale", "Derecho Gubernamental", 18L),
                new SubsectorInput(355L, "Human Rights Protection Law / Children Rights", "Droits de l'Homme", "Protección de los Derechos Humanos", 18L),
                new SubsectorInput(363L, "Immigration Law", "Loi sur l'immigration", "Derecho Inmigratorio", 18L),
                new SubsectorInput(348L, "Insurance Law", "Droit des assurances", "Derecho de Seguros", 18L),
                new SubsectorInput(366L, "International Law", "Droit international", "Derecho Internacional", 18L),
                new SubsectorInput(346L, "Judges", "Juges", "Jueces", 18L),
                new SubsectorInput(362L, "Labour Law", "Code du travail", "Derecho del Trabajo / Laboral", 18L),
                new SubsectorInput(340L, "Law / Legislation / Legal Framework / Regulation / RIA / Reform", "Droit / Législation / Cadre légal / Règlement", "Ley / Legislación / Marco Legal / Reglamento", 18L),
                new SubsectorInput(341L, "Law Harmonisation / Approximation / Pre-accession / Acquis communautaire / European Law", "Harmonisation / Rapprochement des législations / Pré-accession / Acquis communautaire", "Harmonización Legal / Aproximación / Pre accesión / Acervo Comunitario", 18L),
                new SubsectorInput(364L, "Patent / Trademark / Copyright / Intellectual Property", "Brevets / Marques / Copyright / Propriété intellectuelle", "Patentes / Marca Registrada / Copyright / Propiedad Intelectual", 18L),
                new SubsectorInput(343L, "Property / Estates / Trusts", "Propriété / Immobilier / Sociétés fiduciaires", "Propiedad / Bienes Inmuebles/ Depósitos", 18L),
                new SubsectorInput(359L, "Secured Transactions / Privacy / Data protection", "Opérations sécurisées / Protection des données / Vie privée", "Transacciones Aseguradas / Privacidad / Protección de datos", 18L),
                new SubsectorInput(360L, "Securities", "Titres de placement", "Obligaciones", 18L),
                new SubsectorInput(361L, "State Enterprises Law", "Entreprises publiques", "Derecho de las Empresas del Estado", 18L),
                new SubsectorInput(358L, "Taxation / Income / Chartered Surveyors", "Taxes / Impôts / Experts comptables", "Impuestos / Tasas / Contadores", 18L),
                new SubsectorInput(344L, "Torts / Dispute Settlement / Dialogue / Arbitration / Litigation / Mediation", "Règlement de conflit / Arbitrage / Litige / Médiation", "Agravio/ Daño / Juicio / Acuerdo Amigable / Arbitraje / Litigio / Mediación", 18L),
                new SubsectorInput(353L, "Transport Law", "Code des transports", "Derecho de los Transportes", 18L),

                new SubsectorInput(369L, "Air / Aviation / Airport / Space / Spaceships / Spacecraft", "Air / Aviation / Aéroport / Espace / Vaisseaux spatiaux / Engins spatiaux", "Aire / Aviación / Aeropuerto / Espacio / Naves espaciales / Vehículos espaciales", 19L),
                new SubsectorInput(531L, "Freight / Cargo", "Fret / Marchandises", "Carga / Cargo", 19L),
                new SubsectorInput(375L, "Inter-modal / Multi-modal", "Inter-modal / Multi-modal", "Inter-modal / Multi-modal", 19L),
                new SubsectorInput(374L, "Investment / Finances", "Investissement / Finance", "Inversión / Finanzas", 19L),
                new SubsectorInput(380L, "Logistics / Supply chain management / Value chain", "Logistique / Gestion de la chaîne d’approvisionnement / Chaîne de valeur", "Logística / Gestión de la cadena de suministro / Cadena de valor", 19L),
                new SubsectorInput(379L, "Navigational Aids", "Aides à la navigation", "Ayudas marítimas", 19L),
                new SubsectorInput(368L, "Policy & Planning / Models / Urban Mobility / Smart Cities / Sustainable transport/ E-mobility", "Politiques et planification / Modèles / Mobilité urbaine / Villes intelligentes / Transports durables / E-mobilité", "Políticas y planificación / Modelos / Movilidad urbana / Ciudades inteligentes / Transportes sostenibles / E-movilidad", 19L),
                new SubsectorInput(400L, "Procurement / Equipment / Vehicles / Maintenance", "Biens / Equipements / Véhicules", "Abastecimiento /Equipamiento / Vehículos", 19L),
                new SubsectorInput(371L, "Rail", "Chemin de fer", "Vías del tren", 19L),
                new SubsectorInput(377L, "Regulation & Pricing", "Règlement et tarification", "Reglamento & Tarifas", 19L),
                new SubsectorInput(370L, "Road / Public transportation / Transfer", "Route / Transports publics / Transfert", "Carretera / Transporte público / Traslado", 19L),
                new SubsectorInput(376L, "Safety / Pedestrian", "Sécurité / Piéton", "Seguridad / Peatones", 19L),
                new SubsectorInput(373L, "Traffic / Origin-Destination Surveys", "Trafic / Origine - Destination des marchandises", "Tráfico / Mediciones Origen-destino", 19L),
                new SubsectorInput(378L, "Warehouses & Storage", "Entrepôts et stockage", "Depósitos & Almacenamiento", 19L),
                new SubsectorInput(372L, "Water Navigation / Ports / Shipping", "Navigation fluviale / Navigation maritime / Ports / Fret", "Aguas navegables / Puertos / Marítimo", 19L),

                new SubsectorInput(391L, "Community Participation / Employment", "Participation communautaire / Emploi", "Participación de la Comunidad / Empleo", 20L),
                new SubsectorInput(384L, "Energy", "Energie", "Energía", 20L),
                new SubsectorInput(386L, "Engineering / Infrastructure / Implementation / Building / Works Supervision", "Ingénierie / Mise en oeuvre / Construction / Supervision de travaux", "Ingeniería / Ejecución / Construcción / Supervisión de trabajos", 20L),
                new SubsectorInput(389L, "Finance / Budgeting / Investment / Valuation / Taxation", "Finance / Budget / Investissement / Expertise / Taxation", "Finanzas / Presupuesto / Inversión / Estimación / Tasación", 20L),
                new SubsectorInput(383L, "Land Use", "Utilisation des sols", "Utilización de la tierra / Afectación de suelos", 20L),
                new SubsectorInput(381L, "Planning / Models / Policies", "Planning / Modèles / Politiques", "Planificación / Modelos / Políticas", 20L),
                new SubsectorInput(395L, "Property / Facilities / Asset Management", "Propriété / Equipement / Gestion des actifs", "Propiedad / Instalaciones / Gestión del capital", 20L),
                new SubsectorInput(534L, "Public utilities", "Services Publics", "Servicios públicos", 20L),
                new SubsectorInput(392L, "Self-help Programmes", "Programmes d'auto-assistance / Initiatives locales", "Programas de Auto-Ayuda", 20L),
                new SubsectorInput(388L, "Services / Facilities / Maintenance / Cleaning services", "Services / Installations", "Servicios / Facilidades", 20L),
                new SubsectorInput(385L, "Shelter / Housing / Habitat", "Abris / Logement / Habitat", "Refugio / Alojamiento / Vivienda", 20L),
                new SubsectorInput(387L, "Slums", "Bidonvilles", "Chabolas", 20L),
                new SubsectorInput(390L, "Standards & Regulations", "Normes et règlementation", "Normas & Reglamentos", 20L),
                new SubsectorInput(394L, "Street Furniture / Equipment / Supply", "Mobilier urbain / Équipement / Fournitures", "Mobiliario urbano / Equipamiento / Suministros", 20L),
                new SubsectorInput(382L, "Survey", "Sondages / Etudes", "Encuesta / Estudio", 20L),
                new SubsectorInput(393L, "Transport", "Transports", "Transporte", 20L),
                new SubsectorInput(533L, "Water / Drainage / Irrigation / Flood / Well / Hydrology / Sewage / Sanitation networks / Potable Water / Water Supply / Wastewater", "Eau / Drainage / Irrigation / Inondation / Puits / Hydrologie / Égouts / Réseaux d’assainissement / Eau potable / Approvisionnement en eau / Eaux usées", "Agua / Drenaje / Riego / Inundación / Pozos / Hidrología / Alcantarillado / Redes de saneamiento / Agua potable / Abastecimiento de agua / Aguas residuales", 20L),

                new SubsectorInput(406L, "Comparative testing", "Tests comparatifs", "Tests comparativos", 21L),
                new SubsectorInput(416L, "Consumer associations / groups", "Association des consommateurs", "Asociaciones de consumidores / Grupos", 21L),
                new SubsectorInput(403L, "Consumer protection / Consumer policy / Market surveillance", "Protection des consommateurs / Politique des consommateurs / Consommateurs", "Protección del consumidor / Política del consumidor / Consumidor", 21L),
                new SubsectorInput(414L, "Consumer redress", "Indemnisation des consommateurs", "Compensación del consumidor", 21L),
                new SubsectorInput(415L, "Consumer representation", "Représentation des consommateurs", "Representación del consumidor", 21L),
                new SubsectorInput(407L, "Contracts / Warranty / Claims & complaints", "Contrats / Clauses contractuelles", "Contractos / Términos del contracto", 21L),
                new SubsectorInput(412L, "Cosmetics / Household appliances / Cars / Dangerous substances/ Hazardous substances / Toys", "Cosmétiques / Produits domestiques / Voitures / Substances dangereuses / Produits inflammables / Jouets", "Cosméticos / Aparatos domésticos / Coches / Sustancias peligrosas o arriesgadas / Juguetes", 21L),
                new SubsectorInput(404L, "Information / Labelling", "Information / Etiquetage", "Información / Etiquetado", 21L),
                new SubsectorInput(408L, "Marketing practices / Sales methods", "Pratiques commerciales / Méthodes de vente", "Prácticas de Marketing / Métodos de ventas", 21L),
                new SubsectorInput(409L, "Product quality control", "Contrôle qualité des produits", "Control de calidad del producto", 21L),
                new SubsectorInput(411L, "Product Safety / Food Safety / SPS (Sanitary / Phytosanitary)", "Sécurité des produits", "Seguridad del producto", 21L),
                new SubsectorInput(410L, "Standardisation / Certification / Accreditation / Conformity assessment / Metrology / Laboratory", "Normes / Certifications / Etudes de conformité", "Normalización / Certificación / Valoración de conformidad", 21L),
                new SubsectorInput(417L, "Sustainable consumption", "Consommation durable", "Consumo sostenible", 21L),
                new SubsectorInput(405L, "Warnings", "Avertissements", "Consejos / Avisos / Advertencias", 21L)
        );
    }

    private record SubsectorInput(Long id, String nameEn, String nameFr, String nameEs, Long mainsectorId) {}
}
