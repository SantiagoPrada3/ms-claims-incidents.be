package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.dto;

import lombok.Data;

@Data
public class MaterialUsedDTO {
    private String productId;
    private Integer quantity;
    private String unit;
}