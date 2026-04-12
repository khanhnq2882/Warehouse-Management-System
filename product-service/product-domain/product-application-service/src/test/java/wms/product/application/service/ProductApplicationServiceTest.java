package wms.product.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.dto.CreateProductResponse;
import wms.product.application.service.mapper.ProductDataMapper;
import wms.product.application.service.ports.input.ProductApplicationService;
import wms.product.application.service.ports.output.repository.CategoryRepository;
import wms.product.application.service.ports.output.repository.ProductRepository;
import wms.product.application.service.ports.output.repository.SupplierRepository;
import wms.common.service.domain.valueobject.CategoryId;
import wms.common.service.domain.valueobject.ProductId;
import wms.common.service.domain.valueobject.SupplierId;
import wms.product.domain.core.entity.Category;
import wms.product.domain.core.entity.Product;
import wms.product.domain.core.entity.Supplier;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = ProductTestConfiguration.class)
public class ProductApplicationServiceTest {
    @Autowired
    private ProductApplicationService productApplicationService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private CreateProductRequest createProductRequest;
    @Autowired
    private ProductDataMapper productDataMapper;
    private final String PRODUCT_ID = "0047a375-d089-4077-b96b-0e9302715f2d";
    private final String CATEGORY_ID = "7ba00bc1-f3c7-4237-840b-faa7d1e420be";
    private final String SUPPLIER_ID = "408d3d5a-4963-42ba-9234-68630705b234";

    @BeforeAll
    public void init() {
        createProductRequest = CreateProductRequest.builder()
                .sku("QJN-XA-28")
                .productName("Jeans")
                .productPrice(BigDecimal.valueOf(100))
                .productDescription("Latest Women's Jeans Models in 2025")
                .categoryId(UUID.fromString(CATEGORY_ID))
                .supplierId(UUID.fromString(SUPPLIER_ID))
                .build();
        Category category = new Category(new CategoryId(UUID.fromString(CATEGORY_ID)));
        Supplier supplier = new Supplier(new SupplierId(UUID.fromString(SUPPLIER_ID)));
        Product product = productDataMapper.createProductRequestToProduct(createProductRequest);
        product.setId(new ProductId(UUID.fromString(PRODUCT_ID)));
        when(categoryRepository.findCategory(UUID.fromString(CATEGORY_ID))).thenReturn(Optional.of(category));
        when(supplierRepository.findSupplier(UUID.fromString(SUPPLIER_ID))).thenReturn(Optional.of(supplier));
        when(productRepository.save(any(Product.class))).thenReturn(product);
    }

    @Test
    public void testCreateProduct() {
        CreateProductResponse createProductResponse = productApplicationService.createProduct(createProductRequest);
        Assertions.assertEquals("New product created successfully!", createProductResponse.getMessage());
    }
}
