package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.ComplaintResponseService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.ComplaintResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/complaint-responses")
public class ComplaintResponseRest {

    private final ComplaintResponseService complaintResponseService;

    public ComplaintResponseRest(ComplaintResponseService complaintResponseService) {
        this.complaintResponseService = complaintResponseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ComplaintResponseDTO> getAllComplaintResponses() {
        return complaintResponseService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintResponseDTO> getComplaintResponseById(@PathVariable String id) {
        return complaintResponseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ComplaintResponseDTO> createComplaintResponse(@RequestBody ComplaintResponseDTO complaintResponseDTO) {
        return complaintResponseService.save(complaintResponseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintResponseDTO> updateComplaintResponse(@PathVariable String id,
            @RequestBody ComplaintResponseDTO complaintResponseDTO) {
        return complaintResponseService.update(id, complaintResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteComplaintResponse(@PathVariable String id) {
        return complaintResponseService.deleteById(id);
    }
}