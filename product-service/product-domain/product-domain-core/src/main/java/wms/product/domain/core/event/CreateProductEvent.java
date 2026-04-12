package wms.product.domain.core.event;

import wms.product.domain.core.entity.Product;
import java.time.ZonedDateTime;

public class CreateProductEvent extends ProductEvent{
    public CreateProductEvent(Product product, ZonedDateTime createdAt) {
        super(product, createdAt);
    }
}
