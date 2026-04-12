package wms.product.data.access;

import org.springframework.stereotype.Component;
import wms.product.application.service.ports.output.repository.ProductRepository;
import wms.product.data.access.entity.ProductEntity;
import wms.product.data.access.mapper.ProductDataAccessMapper;
import wms.product.data.access.repository.ProductJpaRepository;
import wms.product.domain.core.entity.Product;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository,
                                 ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productDataAccessMapper.productMapToProductEntity(product);
        return productDataAccessMapper.productEntityToProduct(productJpaRepository.save(productEntity));
    }
}
