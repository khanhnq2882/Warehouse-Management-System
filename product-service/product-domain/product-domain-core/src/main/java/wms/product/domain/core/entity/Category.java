package wms.product.domain.core.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.service.domain.valueobject.CategoryId;
import java.time.LocalDateTime;

public class Category extends AggregateRoot<CategoryId> {
    private String categoryName;
    private String categoryDescription;

    private Category(Builder builder) {
        super.setId(builder.categoryId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        categoryName = builder.categoryName;
        categoryDescription = builder.categoryDescription;
    }

    public Category(CategoryId categoryId) {
        super.setId(categoryId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public static final class Builder {
        private CategoryId categoryId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String categoryName;
        private String categoryDescription;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder categoryId(CategoryId val) {
            categoryId = val;
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

        public Builder categoryName(String val) {
            categoryName = val;
            return this;
        }

        public Builder categoryDescription(String val) {
            categoryDescription = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
