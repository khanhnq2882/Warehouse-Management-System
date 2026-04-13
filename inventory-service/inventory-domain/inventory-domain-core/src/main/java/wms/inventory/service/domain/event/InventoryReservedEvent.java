package wms.inventory.service.domain.event;

import wms.inventory.service.domain.entity.InventoryItem;
import java.time.ZonedDateTime;

public class InventoryReservedEvent extends InventoryEvent{
    public InventoryReservedEvent(InventoryItem inventoryItem, ZonedDateTime createdAt) {
        super(inventoryItem, createdAt);
    }
}