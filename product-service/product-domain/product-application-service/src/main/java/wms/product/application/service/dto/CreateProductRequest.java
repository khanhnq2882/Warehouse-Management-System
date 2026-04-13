package wms.product.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public final class CreateProductRequest {
    private final @NotNull String sku;
    private final @NotNull String productName;
    private final @NotNull String productDescription;
    private final @NotNull BigDecimal productPrice;
    private final @NotNull UUID categoryId;
}
