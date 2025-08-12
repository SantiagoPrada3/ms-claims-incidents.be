package pe.edu.vallegrande.vg_ms_claims_incidents.domain.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MaterialUsed {
    @Field("product_id")
    private String productId;
    private Integer quantity;
    private String unit;
}