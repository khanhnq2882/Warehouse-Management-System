package wms.inventory.service.domain.valueobject;

import wms.common.service.domain.valueobject.BaseId;
import java.util.UUID;

public class InventoryItemId extends BaseId<UUID> {
    public InventoryItemId(UUID value) {
        super(value);
    }
}
