package wms.product.application.service.ports.output.repository;

import wms.product.domain.core.entity.Category;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository{
    Optional<Category> findCategory(UUID categoryId);
}
