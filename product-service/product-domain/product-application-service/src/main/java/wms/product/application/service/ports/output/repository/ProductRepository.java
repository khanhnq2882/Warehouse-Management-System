package wms.product.application.service.ports.output.repository;

import wms.product.domain.core.entity.Product;

public interface ProductRepository{
    Product save(Product product);
}
