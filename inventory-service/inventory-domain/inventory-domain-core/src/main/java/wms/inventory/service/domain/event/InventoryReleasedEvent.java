package wms.inventory.service.domain.event;

import wms.inventory.service.domain.entity.InventoryItem;
import java.time.ZonedDateTime;

public class InventoryReleasedEvent extends InventoryEvent{
    public InventoryReleasedEvent(InventoryItem inventoryItem, ZonedDateTime createdAt) {
        super(inventoryItem, createdAt);
    }
}