package wms.product.data.access.mapper;

import org.springframework.stereotype.Component;
import wms.product.data.access.entity.CategoryEntity;
import wms.product.data.access.entity.ProductEntity;
import wms.product.domain.core.valueobject.CategoryId;
import wms.common.service.domain.valueobject.Money;
import wms.common.service.domain.valueobject.ProductId;
import wms.product.domain.core.entity.Category;
import wms.product.domain.core.entity.Product;

@Component
public class ProductDataAccessMapper {

    public ProductEntity productMapToProductEntity(Product product) {
        return ProductEntity.builder()
                .productId(product.getId().getValue())
                .sku(product.getSku())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice().amount())
                .productStatus(product.getProductStatus())
                .category(mapCategoryToCategoryEntity(new Category(product.getCategoryId())))
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productId(new ProductId(productEntity.getProductId()))
                .sku(productEntity.getSku())
                .productName(productEntity.getProductName())
                .productPrice(new Money(productEntity.getProductPrice()))
                .productDescription(productEntity.getProductDescription())
                .categoryId(new CategoryId(productEntity.getCategory().getCategoryId()))
                .productStatus(productEntity.getProductStatus())
                .build();
    }

    public CategoryEntity mapCategoryToCategoryEntity(Category category) {
        return CategoryEntity.builder()
                .categoryId(category.getId().getValue())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .build();
    }
}
