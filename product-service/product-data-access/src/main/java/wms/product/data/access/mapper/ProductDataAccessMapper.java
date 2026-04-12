package wms.product.data.access.mapper;

import org.springframework.stereotype.Component;
import wms.product.data.access.entity.CategoryEntity;
import wms.product.data.access.entity.ProductEntity;
import wms.product.data.access.entity.SupplierAddressEntity;
import wms.product.data.access.entity.SupplierEntity;
import wms.common.service.domain.valueobject.CategoryId;
import wms.common.service.domain.valueobject.Money;
import wms.common.service.domain.valueobject.ProductId;
import wms.common.service.domain.valueobject.SupplierId;
import wms.product.domain.core.entity.Category;
import wms.product.domain.core.entity.Product;
import wms.product.domain.core.entity.Supplier;
import wms.product.domain.core.value.object.SupplierAddress;
import java.util.ArrayList;
import java.util.Arrays;

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
                .supplier(mapSupplierToSupplierEntity(new Supplier(product.getSupplierId())))
                .failureMessages(product.getFailureMessages() != null ? String.join("," , product.getFailureMessages()) : "")
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
                .supplierId(new SupplierId(productEntity.getSupplier().getSupplierId()))
                .productStatus(productEntity.getProductStatus())
                .failureMessages(productEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(productEntity.getFailureMessages().split(","))))
                .build();
    }

    public CategoryEntity mapCategoryToCategoryEntity(Category category) {
        return CategoryEntity.builder()
                .categoryId(category.getId().getValue())
                .categoryName(category.getCategoryName())
                .categoryDescription(category.getCategoryDescription())
                .build();
    }

    public SupplierEntity mapSupplierToSupplierEntity(Supplier supplier) {
        return SupplierEntity.builder()
                .supplierId(supplier.getId().getValue())
                .supplierCode(supplier.getSupplierCode())
                .supplierName(supplier.getSupplierName())
                .supplierDescription(supplier.getSupplierDescription())
                .contactName(supplier.getContactName())
                .contactPhone(supplier.getContactPhone())
                .email(supplier.getEmail())
                .supplierStatus(supplier.getSupplierStatus())
                .supplierAddress(mapSupplierAddressToSupplierAddressEntity(supplier.getSupplierAddress()))
                .build();
    }

    public SupplierAddressEntity mapSupplierAddressToSupplierAddressEntity(SupplierAddress supplierAddress) {
        return SupplierAddressEntity.builder()
                .supplierId(supplierAddress.id())
                .street(supplierAddress.street())
                .city(supplierAddress.city())
                .postalCode(supplierAddress.postalCode())
                .build();
    }


}
