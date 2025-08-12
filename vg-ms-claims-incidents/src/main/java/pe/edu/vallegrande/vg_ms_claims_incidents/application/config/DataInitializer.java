package pe.edu.vallegrande.vg_ms_claims_incidents.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.vg_ms_claims_incidents.domain.models.ComplaintCategory;
import pe.edu.vallegrande.vg_ms_claims_incidents.domain.models.IncidentType;
import pe.edu.vallegrande.vg_ms_claims_incidents.domain.models.Complaint;
import pe.edu.vallegrande.vg_ms_claims_incidents.domain.models.Incident;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.repository.ComplaintCategoryRepository;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.repository.IncidentTypeRepository;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.repository.ComplaintRepository;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.repository.IncidentRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import org.bson.types.ObjectId;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

        @Value("${app.data.initialize:false}")
        private boolean initializeData;

        @Autowired
        private ComplaintCategoryRepository complaintCategoryRepository;

        @Autowired
        private IncidentTypeRepository incidentTypeRepository;

        @Autowired
        private ComplaintRepository complaintRepository;

        @Autowired
        private IncidentRepository incidentRepository;

        @Override
        public void run(String... args) {
                if (!initializeData) {
                        log.info("Data initialization is disabled");
                        return;
                }

                log.info("Iniciando carga de datos de prueba...");

                // Inicializar categorías de reclamos
                ComplaintCategory category1 = new ComplaintCategory();
                category1.setOrganizationId("ORG001");
                category1.setCategoryCode("CAT001");
                category1.setCategoryName("Fuga de Agua");
                category1.setDescription("Reclamos relacionados con fugas de agua");
                category1.setPriorityLevel("HIGH");
                category1.setMaxResponseTime(24);
                category1.setStatus("ACTIVE");
                category1.setCreatedAt(Instant.now());

                ComplaintCategory category2 = new ComplaintCategory();
                category2.setOrganizationId("ORG001");
                category2.setCategoryCode("CAT002");
                category2.setCategoryName("Presión Baja");
                category2.setDescription("Reclamos por baja presión de agua");
                category2.setPriorityLevel("MEDIUM");
                category2.setMaxResponseTime(48);
                category2.setStatus("ACTIVE");
                category2.setCreatedAt(Instant.now());

                // Inicializar tipos de incidentes
                IncidentType incidentType1 = new IncidentType();
                incidentType1.setOrganizationId("ORG001");
                incidentType1.setTypeCode("INC001");
                incidentType1.setTypeName("Ruptura de Tubería");
                incidentType1.setDescription("Incidentes por ruptura de tubería principal");
                incidentType1.setPriorityLevel("CRITICAL");
                incidentType1.setEstimatedResolutionTime(72);
                incidentType1.setRequiresExternalService(true);
                incidentType1.setStatus("ACTIVE");
                incidentType1.setCreatedAt(Instant.now());

                IncidentType incidentType2 = new IncidentType();
                incidentType2.setOrganizationId("ORG001");
                incidentType2.setTypeCode("INC002");
                incidentType2.setTypeName("Contaminación");
                incidentType2.setDescription("Incidentes por contaminación del agua");
                incidentType2.setPriorityLevel("CRITICAL");
                incidentType2.setEstimatedResolutionTime(24);
                incidentType2.setRequiresExternalService(true);
                incidentType2.setStatus("ACTIVE");
                incidentType2.setCreatedAt(Instant.now());

                // Inicializar quejas de prueba
                Complaint complaint1 = new Complaint();
                complaint1.setOrganizationId(new ObjectId("507f1f77bcf86cd799439011"));
                complaint1.setComplaintCode("REC001");
                complaint1.setUserId(new ObjectId("507f1f77bcf86cd799439012"));
                complaint1.setCategoryId(new ObjectId("507f1f77bcf86cd799439013"));
                complaint1.setWaterBoxId(new ObjectId("507f1f77bcf86cd799439014"));
                complaint1.setComplaintDate(Instant.now());
                complaint1.setSubject("Fuga de agua en calle principal");
                complaint1.setDescription("Hay una fuga de agua en la calle principal que está causando desperdicio");
                complaint1.setPriority("HIGH");
                complaint1.setStatus("PENDING");
                complaint1.setCreatedAt(Instant.now());

                Complaint complaint2 = new Complaint();
                complaint2.setOrganizationId(new ObjectId("507f1f77bcf86cd799439015"));
                complaint2.setComplaintCode("REC002");
                complaint2.setUserId(new ObjectId("507f1f77bcf86cd799439016"));
                complaint2.setCategoryId(new ObjectId("507f1f77bcf86cd799439017"));
                complaint2.setWaterBoxId(new ObjectId("507f1f77bcf86cd799439018"));
                complaint2.setComplaintDate(Instant.now());
                complaint2.setSubject("Presión de agua muy baja");
                complaint2.setDescription("La presión del agua es muy baja en todo el edificio");
                complaint2.setPriority("MEDIUM");
                complaint2.setStatus("PENDING");
                complaint2.setCreatedAt(Instant.now());

                // Inicializar incidentes de prueba con el nuevo formato simplificado
                Incident incident1 = new Incident();
                incident1.setIncidentCode("INC001");
                incident1.setOrganizationId("64abc123def4567890123456");
                incident1.setIncidentTypeId("64def456abc7890123456789");
                incident1.setIncidentCategory("DISTRIBUCION");
                incident1.setZoneId("64def123abc4567890123456");
                incident1.setIncidentDate(Instant.now());
                incident1.setTitle("Fuga en Calle Los Pinos");
                incident1.setDescription("Fuga importante en tubería principal, afecta suministro a 30 viviendas");
                incident1.setSeverity("HIGH");
                incident1.setStatus("RESOLVED");
                incident1.setAffectedBoxesCount(30);
                incident1.setReportedByUserId("64abc789def1234567890123");
                incident1.setAssignedToUserId("64abc456def7890123456789");
                incident1.setResolvedByUserId("64abc456def7890123456789");
                incident1.setResolved(true);
                incident1.setResolutionNotes("Reparación temporal con válvula de bypass");
                incident1.setRecordStatus("ACTIVE");

                Incident incident2 = new Incident();
                incident2.setIncidentCode("INC002");
                incident2.setOrganizationId("64abc123def4567890123456");
                incident2.setIncidentTypeId("64def456abc7890123456790");
                incident2.setIncidentCategory("CALIDAD");
                incident2.setZoneId("64def123abc4567890123457");
                incident2.setIncidentDate(Instant.now());
                incident2.setTitle("Nivel de cloro bajo");
                incident2.setDescription("Nivel de cloro bajo en planta de tratamiento");
                incident2.setSeverity("MEDIUM");
                incident2.setStatus("IN_PROGRESS");
                incident2.setAffectedBoxesCount(15);
                incident2.setReportedByUserId("64abc789def1234567890124");
                incident2.setAssignedToUserId("64abc456def7890123456790");
                incident2.setResolved(false);
                incident2.setRecordStatus("ACTIVE");

                Incident incident3 = new Incident();
                incident3.setIncidentCode("INC003");
                incident3.setOrganizationId("64abc123def4567890123456");
                incident3.setIncidentTypeId("64def456abc7890123456792");
                incident3.setIncidentCategory("GENERAL");
                incident3.setZoneId("64def123abc4567890123458");
                incident3.setIncidentDate(Instant.now());
                incident3.setTitle("Corte programado de suministro");
                incident3.setDescription("Corte programado de suministro por mantenimiento");
                incident3.setSeverity("LOW");
                incident3.setStatus("REPORTED");
                incident3.setAffectedBoxesCount(5);
                incident3.setReportedByUserId("64abc789def1234567890125");
                incident3.setAssignedToUserId("64abc456def7890123456792");
                incident3.setResolved(false);
                incident3.setRecordStatus("ACTIVE");

                // Guardar datos usando repositorios reactivos
                log.info("Guardando datos de prueba...");

                complaintCategoryRepository.save(category1)
                                .doOnSuccess(cat -> log.info("Categoría 1 guardada: {}", cat.getCategoryName()))
                                .subscribe();

                complaintCategoryRepository.save(category2)
                                .doOnSuccess(cat -> log.info("Categoría 2 guardada: {}", cat.getCategoryName()))
                                .subscribe();

                incidentTypeRepository.save(incidentType1)
                                .doOnSuccess(inc -> log.info("Tipo de incidente 1 guardado: {}", inc.getTypeName()))
                                .subscribe();

                incidentTypeRepository.save(incidentType2)
                                .doOnSuccess(inc -> log.info("Tipo de incidente 2 guardado: {}", inc.getTypeName()))
                                .subscribe();

                complaintRepository.save(complaint1)
                                .doOnSuccess(comp -> log.info("Queja 1 guardada: {}", comp.getComplaintCode()))
                                .subscribe();

                complaintRepository.save(complaint2)
                                .doOnSuccess(comp -> log.info("Queja 2 guardada: {}", comp.getComplaintCode()))
                                .subscribe();

                incidentRepository.save(incident1)
                                .doOnSuccess(inc -> log.info("Incidente 1 guardado: {}", inc.getIncidentCode()))
                                .subscribe();

                incidentRepository.save(incident2)
                                .doOnSuccess(inc -> log.info("Incidente 2 guardado: {}", inc.getIncidentCode()))
                                .subscribe();

                incidentRepository.save(incident3)
                                .doOnSuccess(inc -> log.info("Incidente 3 guardado: {}", inc.getIncidentCode()))
                                .subscribe();

                log.info("Carga de datos de prueba completada");
        }
}