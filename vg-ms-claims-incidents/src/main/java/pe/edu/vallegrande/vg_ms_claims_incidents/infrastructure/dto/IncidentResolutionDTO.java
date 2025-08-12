package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto;

import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class IncidentResolutionDTO {
    private String id;
    private String incidentId;
    private Instant resolutionDate;
    private String resolutionType;
    private String actionsTaken;
    private List<MaterialUsedDTO> materialsUsed;
    private Integer laborHours;
    private Double totalCost;
    private String resolvedByUserId;
    private Boolean qualityCheck;
    private Boolean followUpRequired;
    private String resolutionNotes;
    private Instant createdAt;
}
