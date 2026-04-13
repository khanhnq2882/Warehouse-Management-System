package wms.inventory.service.domain.event;

import wms.inventory.service.domain.entity.InventoryItem;
import java.time.ZonedDateTime;

public class InventoryDeductedEvent extends InventoryEvent{
    public InventoryDeductedEvent(InventoryItem inventoryItem, ZonedDateTime createdAt) {
        super(inventoryItem, createdAt);
    }
}