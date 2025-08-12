package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.rest;

import pe.edu.vallegrande.vg_ms_claims_incidents.application.services.ComplaintCategoryService;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto.ComplaintCategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/complaint-categories")
public class ComplaintCategoryRest {

    private final ComplaintCategoryService complaintCategoryService;

    public ComplaintCategoryRest(ComplaintCategoryService complaintCategoryService) {
        this.complaintCategoryService = complaintCategoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ComplaintCategoryDTO> getAllComplaintCategories() {
        return complaintCategoryService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintCategoryDTO> getComplaintCategoryById(@PathVariable String id) {
        return complaintCategoryService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ComplaintCategoryDTO> createComplaintCategory(@RequestBody ComplaintCategoryDTO complaintCategoryDTO) {
        return complaintCategoryService.save(complaintCategoryDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintCategoryDTO> updateComplaintCategory(@PathVariable String id,
            @RequestBody ComplaintCategoryDTO complaintCategoryDTO) {
        return complaintCategoryService.update(id, complaintCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteComplaintCategory(@PathVariable String id) {
        return complaintCategoryService.deleteById(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ComplaintCategoryDTO> restoreComplaintCategory(@PathVariable String id) {
        return complaintCategoryService.restoreById(id);
    }
}