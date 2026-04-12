package wms.product.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateProductResponse {
    private final UUID productId;
    private final String message;
}
