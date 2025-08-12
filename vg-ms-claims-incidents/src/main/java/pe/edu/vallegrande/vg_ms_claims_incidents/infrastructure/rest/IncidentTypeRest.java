package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.IncidentTypeService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.IncidentTypeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/incident-types")
public class IncidentTypeRest {

    private final IncidentTypeService incidentTypeService;

    public IncidentTypeRest(IncidentTypeService incidentTypeService) {
        this.incidentTypeService = incidentTypeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentTypeDTO> getAllIncidentTypes() {
        return incidentTypeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentTypeDTO> getIncidentTypeById(@PathVariable String id) {
        return incidentTypeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IncidentTypeDTO> createIncidentType(@RequestBody IncidentTypeDTO incidentTypeDTO) {
        return incidentTypeService.save(incidentTypeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentTypeDTO> updateIncidentType(@PathVariable String id,
            @RequestBody IncidentTypeDTO incidentTypeDTO) {
        return incidentTypeService.update(id, incidentTypeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIncidentType(@PathVariable String id) {
        return incidentTypeService.deleteById(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentTypeDTO> restoreIncidentType(@PathVariable String id) {
        return incidentTypeService.restoreById(id);
    }
}