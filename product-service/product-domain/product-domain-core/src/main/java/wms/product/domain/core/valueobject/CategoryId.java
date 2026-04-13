package wms.product.domain.core.valueobject;

import wms.common.service.domain.valueobject.BaseId;
import java.util.UUID;

public class CategoryId extends BaseId<UUID> {
    public CategoryId(UUID value) {
        super(value);
    }
}
