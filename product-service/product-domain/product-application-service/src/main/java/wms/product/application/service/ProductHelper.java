package wms.product.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.mapper.ProductDataMapper;
import wms.product.application.service.ports.output.repository.CategoryRepository;
import wms.product.application.service.ports.output.repository.ProductRepository;
import wms.product.application.service.ports.output.repository.SupplierRepository;
import wms.product.domain.core.ProductDomainService;
import wms.product.domain.core.entity.Category;
import wms.product.domain.core.entity.Product;
import wms.product.domain.core.entity.Supplier;
import wms.product.domain.core.event.CreateProductEvent;
import wms.product.domain.core.exception.ProductDomainException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ProductHelper {
    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductDataMapper productDataMapper;

    public ProductHelper(ProductDomainService productDomainService,
                         ProductRepository productRepository,
                         CategoryRepository categoryRepository,
                         SupplierRepository supplierRepository,
                         ProductDataMapper productDataMapper) {
        this.productDomainService = productDomainService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.productDataMapper = productDataMapper;
    }

    @Transactional
    public CreateProductEvent persistProduct(CreateProductRequest createProductRequest) {
        validateCategory(createProductRequest.getCategoryId());
        validateSupplier(createProductRequest.getSupplierId());
        Product product = productDataMapper.createProductRequestToProduct(createProductRequest);
        CreateProductEvent createProductEvent = productDomainService.createProduct(product);
        saveProduct(product);
        log.info("New product is created with id: {}", createProductEvent.getProduct().getId().getValue());
        return createProductEvent;
    }

    private void validateCategory(UUID categoryId) {
        Optional<Category> category = categoryRepository.findCategory(categoryId);
        if (Objects.isNull(category)) {
            log.error("Category with id {} is not found!", categoryId);
            throw new ProductDomainException("Category with id " +categoryId+ " is not found!");
        }
    }

    private void validateSupplier(UUID supplierId) {
        Optional<Supplier> supplier = supplierRepository.findSupplier(supplierId);
        if (Objects.isNull(supplier)) {
            log.error("Supplier with id {} is not found!", supplierId);
            throw new ProductDomainException("Supplier with id " +supplierId+ " is not found!");
        }
    }

    private Product saveProduct(Product product) {
        Product productResult = productRepository.save(product);
        if (productResult == null) {
            log.error("Could not save product!");
            throw new ProductDomainException("Could not save product!");
        }
        log.info("New product is saved with id: {}", productResult.getId().getValue());
        return productResult;
    }

}
