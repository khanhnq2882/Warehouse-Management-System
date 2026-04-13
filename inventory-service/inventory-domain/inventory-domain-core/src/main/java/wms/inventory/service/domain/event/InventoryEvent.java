package wms.inventory.service.domain.event;

import wms.common.service.domain.event.DomainEvent;
import wms.inventory.service.domain.entity.InventoryItem;

import java.time.ZonedDateTime;

public abstract class InventoryEvent implements DomainEvent<InventoryItem> {
    private final InventoryItem inventoryItem;
    private final ZonedDateTime createdAt;

    public InventoryEvent(InventoryItem inventoryItem, ZonedDateTime createdAt) {
        this.inventoryItem = inventoryItem;
        this.createdAt = createdAt;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}