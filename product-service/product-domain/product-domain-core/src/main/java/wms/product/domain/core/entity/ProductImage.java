package wms.product.domain.core.entity;

import wms.common.service.domain.entity.BaseEntity;
import wms.product.domain.core.value.object.ProductImageId;
import java.time.LocalDateTime;

public class ProductImage extends BaseEntity<ProductImageId> {
    private String productImageName;
    private String productImageUrl;
    private byte[] productImageData;

    private ProductImage(Builder builder) {
        super.setId(builder.productImageId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        productImageName = builder.productImageName;
        productImageUrl = builder.productImageUrl;
        productImageData = builder.productImageData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getProductImageName() {
        return productImageName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public byte[] getProductImageData() {
        return productImageData;
    }

    public static final class Builder {
        private ProductImageId productImageId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String productImageName;
        private String productImageUrl;
        private byte[] productImageData;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder productImageId(ProductImageId val) {
            productImageId = val;
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

        public Builder productImageName(String val) {
            productImageName = val;
            return this;
        }

        public Builder productImageUrl(String val) {
            productImageUrl = val;
            return this;
        }

        public Builder productImageData(byte[] val) {
            productImageData = val;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(this);
        }
    }
}
