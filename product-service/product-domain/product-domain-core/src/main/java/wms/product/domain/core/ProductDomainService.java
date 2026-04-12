package wms.product.domain.core;

import wms.product.domain.core.entity.Product;
import wms.product.domain.core.event.CreateProductEvent;

public interface ProductDomainService {
    CreateProductEvent createProduct(Product product);
}
