package wms.product.domain.core.event;

import wms.common.service.domain.event.DomainEvent;
import wms.product.domain.core.entity.Product;
import java.time.ZonedDateTime;

public abstract class ProductEvent implements DomainEvent<Product> {
    private final Product product;
    private final ZonedDateTime createdAt;

    public ProductEvent(Product product, ZonedDateTime createdAt) {
        this.product = product;
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


}
