package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.IncidentService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.IncidentDTO;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.IncidentCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentRest {

    private final IncidentService incidentService;

    public IncidentRest(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, String>> testEndpoint() {
        log.info("Endpoint de prueba llamado");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Endpoint de incidentes funcionando correctamente");
        response.put("status", "OK");
        return Mono.just(response);
    }

    @PostMapping("/test-create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IncidentDTO> testCreateIncident() {
        log.info("Creando incidente de prueba");

        IncidentDTO testIncident = new IncidentDTO();
        testIncident.setIncidentCode("INC001");
        testIncident.setOrganizationId("64abc123def4567890123456");
        testIncident.setIncidentTypeId("64def456abc7890123456789");
        testIncident.setIncidentCategory("DISTRIBUCION");
        testIncident.setZoneId("64def123abc4567890123456");
        testIncident.setTitle("Fuga en Calle Los Pinos");
        testIncident.setDescription("Fuga importante en tubería principal, afecta suministro a 30 viviendas");
        testIncident.setSeverity("HIGH");
        testIncident.setStatus("REPORTED");
        testIncident.setAffectedBoxesCount(30);
        testIncident.setReportedByUserId("64abc789def1234567890123");
        testIncident.setAssignedToUserId("64abc456def7890123456789");
        testIncident.setResolved(false);
        testIncident.setRecordStatus("ACTIVE");

        log.info("Datos del incidente de prueba: {}", testIncident);

        return incidentService.save(testIncident)
                .doOnSuccess(saved -> log.info("Incidente de prueba creado exitosamente: {}", saved.getIncidentCode()))
                .doOnError(error -> log.error("Error al crear incidente de prueba: {}", error.getMessage(), error));
    }

    @PatchMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentDTO> completeIncident(@PathVariable String id, @RequestBody IncidentDTO incidentDTO) {
        log.info("Completando incidente con ID: {}", id);
        return incidentService.completeIncident(id, incidentDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getAllIncidents() {
        log.info("Obteniendo todos los incidentes");
        return incidentService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentDTO> getIncidentById(@PathVariable String id) {
        log.info("Obteniendo incidente con ID: {}", id);
        return incidentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IncidentDTO> createIncident(@RequestBody IncidentCreateDTO incidentCreateDTO) {
        log.info("=== INICIO CREACIÓN DE INCIDENTE ===");
        log.info("Datos recibidos del frontend:");
        log.info("  - incidentCode: {}", incidentCreateDTO.getIncidentCode());
        log.info("  - organizationId: {}", incidentCreateDTO.getOrganizationId());
        log.info("  - incidentTypeId: {}", incidentCreateDTO.getIncidentTypeId());
        log.info("  - incidentCategory: {}", incidentCreateDTO.getIncidentCategory());
        log.info("  - title: {}", incidentCreateDTO.getTitle());
        log.info("  - description: {}", incidentCreateDTO.getDescription());
        log.info("  - reportedByUserId: {}", incidentCreateDTO.getReportedByUserId());
        log.info("  - zoneId: {}", incidentCreateDTO.getZoneId());
        log.info("  - incidentDate: {}", incidentCreateDTO.getIncidentDate());
        log.info("  - severity: {}", incidentCreateDTO.getSeverity());
        log.info("  - status: {}", incidentCreateDTO.getStatus());
        log.info("  - affectedBoxesCount: {}", incidentCreateDTO.getAffectedBoxesCount());
        log.info("  - assignedToUserId: {}", incidentCreateDTO.getAssignedToUserId());
        log.info("  - resolvedByUserId: {}", incidentCreateDTO.getResolvedByUserId());
        log.info("  - resolved: {}", incidentCreateDTO.getResolved());
        log.info("  - resolutionNotes: {}", incidentCreateDTO.getResolutionNotes());
        log.info("  - recordStatus: {}", incidentCreateDTO.getRecordStatus());

        // Validar campos requeridos antes de la conversión
        validateCreateDTO(incidentCreateDTO);

        // Convertir IncidentCreateDTO a IncidentDTO
        IncidentDTO incidentDTO = convertToIncidentDTO(incidentCreateDTO);

        log.info("IncidentDTO convertido exitosamente, procediendo a guardar...");

        return incidentService.save(incidentDTO)
                .doOnSuccess(saved -> log.info("=== INCIDENTE CREADO EXITOSAMENTE ==="))
                .doOnError(error -> log.error("=== ERROR AL CREAR INCIDENTE: {} ===", error.getMessage()));
    }

    private void validateCreateDTO(IncidentCreateDTO createDTO) {
        log.info("Validando campos del IncidentCreateDTO...");

        if (createDTO.getIncidentCode() == null || createDTO.getIncidentCode().trim().isEmpty()) {
            log.error("incidentCode es null o vacío");
            throw new IllegalArgumentException("El código del incidente es obligatorio");
        }

        if (createDTO.getOrganizationId() == null || createDTO.getOrganizationId().trim().isEmpty()) {
            log.error("organizationId es null o vacío");
            throw new IllegalArgumentException("El ID de la organización es obligatorio");
        }

        if (createDTO.getIncidentTypeId() == null || createDTO.getIncidentTypeId().trim().isEmpty()) {
            log.error("incidentTypeId es null o vacío");
            throw new IllegalArgumentException(
                    "El incidentTypeId de incidente es obligatorio. Asegúrate de enviar el campo 'incidentTypeId' en el JSON");
        }

        if (createDTO.getIncidentCategory() == null || createDTO.getIncidentCategory().trim().isEmpty()) {
            log.error("incidentCategory es null o vacío");
            throw new IllegalArgumentException("La categoría del incidente es obligatoria");
        }

        if (createDTO.getTitle() == null || createDTO.getTitle().trim().isEmpty()) {
            log.error("title es null o vacío");
            throw new IllegalArgumentException("El título del incidente es obligatorio");
        }

        if (createDTO.getDescription() == null || createDTO.getDescription().trim().isEmpty()) {
            log.error("description es null o vacío");
            throw new IllegalArgumentException("La descripción es obligatoria");
        }

        if (createDTO.getReportedByUserId() == null || createDTO.getReportedByUserId().trim().isEmpty()) {
            log.error("reportedByUserId es null o vacío");
            throw new IllegalArgumentException("El ID del usuario que reportó es obligatorio");
        }

        log.info("Todos los campos requeridos están presentes");
    }

    private IncidentDTO convertToIncidentDTO(IncidentCreateDTO createDTO) {
        log.info("Convirtiendo IncidentCreateDTO a IncidentDTO");

        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setOrganizationId(createDTO.getOrganizationId());
        incidentDTO.setIncidentCode(createDTO.getIncidentCode());
        incidentDTO.setIncidentTypeId(createDTO.getIncidentTypeId()); // Ahora ambos usan incidentTypeId
        incidentDTO.setIncidentCategory(createDTO.getIncidentCategory());
        incidentDTO.setZoneId(createDTO.getZoneId());
        incidentDTO.setIncidentDate(createDTO.getIncidentDate());
        incidentDTO.setTitle(createDTO.getTitle());
        incidentDTO.setDescription(createDTO.getDescription());
        incidentDTO.setSeverity(createDTO.getSeverity());
        incidentDTO.setStatus(createDTO.getStatus());
        incidentDTO.setAffectedBoxesCount(createDTO.getAffectedBoxesCount());
        incidentDTO.setReportedByUserId(createDTO.getReportedByUserId());
        incidentDTO.setAssignedToUserId(createDTO.getAssignedToUserId());
        incidentDTO.setResolvedByUserId(createDTO.getResolvedByUserId());
        incidentDTO.setResolved(createDTO.getResolved());
        incidentDTO.setResolutionNotes(createDTO.getResolutionNotes());
        incidentDTO.setRecordStatus(createDTO.getRecordStatus());

        log.info("Conversión completada. IncidentDTO creado con incidentTypeId: {}", incidentDTO.getIncidentTypeId());
        return incidentDTO;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentDTO> updateIncident(@PathVariable String id, @RequestBody IncidentDTO incidentDTO) {
        log.info("Actualizando incidente con ID: {}", id);
        return incidentService.update(id, incidentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIncident(@PathVariable String id) {
        log.info("Eliminando incidente con ID: {}", id);
        return incidentService.deleteById(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentDTO> restoreIncident(@PathVariable String id) {
        log.info("Restaurando incidente con ID: {}", id);
        return incidentService.restoreById(id);
    }

    @GetMapping("/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByOrganization(@PathVariable String organizationId) {
        log.info("Buscando incidentes por organización: {}", organizationId);
        return incidentService.findByOrganizationId(organizationId);
    }

    @GetMapping("/type/{incidentTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByType(@PathVariable String incidentTypeId) {
        log.info("Buscando incidentes por tipoId: {}", incidentTypeId);
        return incidentService.findByIncidentTypeId(incidentTypeId);
    }

    @GetMapping("/severity/{severity}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsBySeverity(@PathVariable String severity) {
        log.info("Buscando incidentes por severidad: {}", severity);
        return incidentService.findBySeverity(severity);
    }

    @GetMapping("/resolved/{resolved}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByResolvedStatus(@PathVariable Boolean resolved) {
        log.info("Buscando incidentes por estado de resolución: {}", resolved);
        return incidentService.findByResolvedStatus(resolved);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByStatus(@PathVariable String status) {
        log.info("Buscando incidentes por estado: {}", status);
        return incidentService.findByStatus(status);
    }

    @GetMapping("/record-status/{recordStatus}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByRecordStatus(@PathVariable String recordStatus) {
        log.info("Buscando incidentes por recordStatus: {}", recordStatus);
        return incidentService.findByRecordStatus(recordStatus);
    }

    @GetMapping("/category/{incidentCategory}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByCategory(@PathVariable String incidentCategory) {
        log.info("Buscando incidentes por categoría: {}", incidentCategory);
        return incidentService.findByIncidentCategory(incidentCategory);
    }

    @GetMapping("/zone/{zoneId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByZone(@PathVariable String zoneId) {
        log.info("Buscando incidentes por zona: {}", zoneId);
        return incidentService.findByZoneId(zoneId);
    }

    @GetMapping("/assigned/{assignedToUserId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByAssignedUser(@PathVariable String assignedToUserId) {
        log.info("Buscando incidentes asignados al usuario: {}", assignedToUserId);
        return incidentService.findByAssignedToUserId(assignedToUserId);
    }

    @GetMapping("/resolved-by/{resolvedByUserId}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentDTO> getIncidentsByResolvedUser(@PathVariable String resolvedByUserId) {
        log.info("Buscando incidentes resueltos por usuario: {}", resolvedByUserId);
        return incidentService.findByResolvedByUserId(resolvedByUserId);
    }

    @PatchMapping("/{id}/resolve")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentDTO> resolveIncident(@PathVariable String id, @RequestBody IncidentDTO incidentDTO) {
        log.info("Resolviendo incidente con ID: {}", id);
        return incidentService.resolveIncident(id, incidentDTO);
    }

    @PostMapping("/validate-json")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, Object>> validateJson(@RequestBody IncidentCreateDTO incidentCreateDTO) {
        log.info("=== VALIDACIÓN DE JSON ===");
        log.info("JSON recibido:");
        log.info("  - incidentCode: '{}'", incidentCreateDTO.getIncidentCode());
        log.info("  - organizationId: '{}'", incidentCreateDTO.getOrganizationId());
        log.info("  - tipoId: '{}'", incidentCreateDTO.getIncidentTypeId());
        log.info("  - incidentCategory: '{}'", incidentCreateDTO.getIncidentCategory());
        log.info("  - title: '{}'", incidentCreateDTO.getTitle());
        log.info("  - description: '{}'", incidentCreateDTO.getDescription());
        log.info("  - reportedByUserId: '{}'", incidentCreateDTO.getReportedByUserId());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "JSON recibido correctamente");
        response.put("data", Map.of(
                "incidentCode", incidentCreateDTO.getIncidentCode(),
                "organizationId", incidentCreateDTO.getOrganizationId(),
                "tipoId", incidentCreateDTO.getIncidentTypeId(),
                "incidentCategory", incidentCreateDTO.getIncidentCategory(),
                "title", incidentCreateDTO.getTitle(),
                "description", incidentCreateDTO.getDescription(),
                "reportedByUserId", incidentCreateDTO.getReportedByUserId()));

        return Mono.just(response);
    }

    @PostMapping("/debug-json")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, Object>> debugJson(@RequestBody String rawJson) {
        log.info("=== DEBUG JSON RECIBIDO ===");
        log.info("JSON raw: {}", rawJson);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "JSON recibido en debug");
        response.put("rawJson", rawJson);
        response.put("length", rawJson.length());

        return Mono.just(response);
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Map<String, String>> ping() {
        log.info("Endpoint ping llamado");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Backend funcionando correctamente");
        response.put("timestamp", Instant.now().toString());
        response.put("status", "OK");
        return Mono.just(response);
    }
}