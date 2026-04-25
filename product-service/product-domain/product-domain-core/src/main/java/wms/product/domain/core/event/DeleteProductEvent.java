package wms.product.domain.core.event;

import wms.product.domain.core.entity.Product;
import java.time.ZonedDateTime;

public class DeleteProductEvent extends ProductEvent{
    public DeleteProductEvent(Product product, ZonedDateTime createdAt) {
        super(product, createdAt);
    }

    @Override
    public void fire() {

    }
}
