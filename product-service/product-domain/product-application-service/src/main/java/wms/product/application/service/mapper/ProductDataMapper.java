package wms.product.application.service.mapper;

import org.springframework.stereotype.Component;
import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.dto.CreateProductResponse;
import wms.product.domain.core.valueobject.CategoryId;
import wms.common.service.domain.valueobject.Money;
import wms.product.domain.core.valueobject.ProductStatus;
import wms.product.domain.core.entity.Product;
import java.time.LocalDateTime;

@Component
public class ProductDataMapper {

    public Product createProductRequestToProduct(CreateProductRequest createProductRequest) {
        return Product.builder()
                .sku(createProductRequest.getSku())
                .productName(createProductRequest.getProductName())
                .productDescription(createProductRequest.getProductDescription())
                .productPrice(new Money(createProductRequest.getProductPrice()))
                .categoryId(new CategoryId(createProductRequest.getCategoryId()))
                .productStatus(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CreateProductResponse productToCreateProductResponse(Product product, String message) {
        return CreateProductResponse.builder()
                .productId(product.getId().getValue())
                .message(message)
                .build();
    }
}
