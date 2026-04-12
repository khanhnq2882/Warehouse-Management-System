package wms.product.domain.core.value.object;

import wms.common.service.domain.valueobject.BaseId;
import java.util.UUID;

public class ProductImageId extends BaseId<UUID> {
    public ProductImageId(UUID value) {
        super(value);
    }
}
