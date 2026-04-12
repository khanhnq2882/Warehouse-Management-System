package wms.product.domain.core.event;

import wms.product.domain.core.entity.Product;
import java.time.ZonedDateTime;

public class UpdateProductEvent extends ProductEvent{
    public UpdateProductEvent(Product product, ZonedDateTime createdAt) {
        super(product, createdAt);
    }
}
