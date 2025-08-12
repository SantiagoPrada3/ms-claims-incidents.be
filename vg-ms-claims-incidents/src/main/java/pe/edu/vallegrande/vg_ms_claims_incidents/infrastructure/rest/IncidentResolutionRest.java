package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.IncidentResolutionService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.IncidentResolutionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/incident-resolutions")
public class IncidentResolutionRest {

    private final IncidentResolutionService incidentResolutionService;

    public IncidentResolutionRest(IncidentResolutionService incidentResolutionService) {
        this.incidentResolutionService = incidentResolutionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<IncidentResolutionDTO> getAllIncidentResolutions() {
        return incidentResolutionService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentResolutionDTO> getIncidentResolutionById(@PathVariable String id) {
        return incidentResolutionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IncidentResolutionDTO> createIncidentResolution(
            @RequestBody IncidentResolutionDTO incidentResolutionDTO) {
        return incidentResolutionService.save(incidentResolutionDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<IncidentResolutionDTO> updateIncidentResolution(@PathVariable String id,
            @RequestBody IncidentResolutionDTO incidentResolutionDTO) {
        return incidentResolutionService.update(id, incidentResolutionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIncidentResolution(@PathVariable String id) {
        return incidentResolutionService.deleteById(id);
    }
}