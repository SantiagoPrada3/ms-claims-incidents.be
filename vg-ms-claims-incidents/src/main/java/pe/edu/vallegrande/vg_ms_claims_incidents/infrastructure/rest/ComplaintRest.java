package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.ComplaintService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.ComplaintDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/v1/complaints")
@CrossOrigin(origins = "*")
public class ComplaintRest {

    private final ComplaintService complaintService;

    public ComplaintRest(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> testEndpoint() {
        log.info("Endpoint de prueba accedido");
        return Mono.just("API funcionando correctamente");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ComplaintDTO> getAllComplaints() {
        log.info("Recibida petición GET para obtener todas las quejas");
        return complaintService.findAll()
                .doOnNext(complaint -> log.info("Enviando queja: {}", complaint.getComplaintCode()))
                .doOnComplete(() -> log.info("Respuesta enviada con todas las quejas"))
                .doOnError(error -> log.error("Error al obtener quejas: {}", error.getMessage()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintDTO> getComplaintById(@PathVariable String id) {
        return complaintService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        log.info("Received request to create new complaint");
        return complaintService.save(complaintDTO)
                .doOnSuccess(complaint -> log.info("Complaint created successfully with ID: {}", complaint.getId()))
                .doOnError(error -> log.error("Error creating complaint: {}", error.getMessage()))
                .onErrorResume(IllegalArgumentException.class,
                        error -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage())))
                .onErrorResume(Exception.class, error -> Mono.error(
                        new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing complaint")));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintDTO> updateComplaint(@PathVariable String id, @RequestBody ComplaintDTO complaintDTO) {
        return complaintService.update(id, complaintDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteComplaint(@PathVariable String id) {
        return complaintService.deleteById(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintDTO> restoreComplaint(@PathVariable String id) {
        return complaintService.restoreById(id);
    }
}