package wms.product.domain.core.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.domain.value.object.*;
import wms.common.service.domain.valueobject.*;
import wms.product.domain.core.exception.ProductDomainException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Product extends AggregateRoot<ProductId> {
    private final String sku;
    private String productName;
    private String productDescription;
    private Money productPrice;
//    private List<ProductImage> productImages;
    private final SupplierId supplierId;
    private final CategoryId categoryId;
    private ProductStatus productStatus;
    private List<String> failureMessages;

    private Product(Builder builder) {
        super.setId(builder.productId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        sku = builder.sku;
        productName = builder.productName;
        productDescription = builder.productDescription;
        productPrice = builder.productPrice;
//        productImages = builder.productImages;
        supplierId = builder.supplierId;
        categoryId = builder.categoryId;
        productStatus = builder.productStatus;
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSku() {
        return sku;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Money getProductPrice() {
        return productPrice;
    }

//    public List<ProductImage> getProductImages() {
//        return productImages;
//    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }
    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public void createProduct() {
        validateProductFields();
        setId(new ProductId(UUID.randomUUID()));
    }

    public void updateProduct() {

    }

    public void deleteProduct() {

    }

    private void validateProductFields() {
        validateProductSku();
        validatePrice();
        validateIdNotExist(categoryId, "Category ID " + categoryId + " is not exists!");
        validateIdNotExist(supplierId, "Supplier ID " + supplierId + " is not exists!");
    }

    private void validateProductSku() {
        Pattern pattern = Pattern.compile("^[A-Z]+(?:-[A-Z0-9]+)*$");
        if (Objects.isNull(sku) || !pattern.matcher(sku).matches())
            throw new ProductDomainException("Product sku is invalid!");
    }

    private void validatePrice() {
        if (Objects.isNull(productPrice) || !productPrice.isGreaterThanZero())
            throw new ProductDomainException("Product price must be greater than 0!");
    }

    private void validateIdNotExist(BaseId baseId, String message) {
        if (Objects.isNull(baseId))
            throw new ProductDomainException(message);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    public static final class Builder {
        private ProductId productId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String sku;
        private String productName;
        private String productDescription;
        private Money productPrice;
//        private List<ProductImage> productImages;
        private SupplierId supplierId;
        private CategoryId categoryId;
        private ProductStatus productStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder productName(String val) {
            productName = val;
            return this;
        }

        public Builder productDescription(String val) {
            productDescription = val;
            return this;
        }

        public Builder productPrice(Money val) {
            productPrice = val;
            return this;
        }

//        public Builder productImages(List<ProductImage> val) {
//            productImages = val;
//            return this;
//        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder categoryId(CategoryId val) {
            categoryId = val;
            return this;
        }

        public Builder productStatus(ProductStatus val) {
            productStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
