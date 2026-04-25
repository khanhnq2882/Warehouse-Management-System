package wms.inventory.service.domain.event;

import wms.common.service.domain.event.DomainEvent;
import wms.inventory.service.domain.entity.InventoryItem;
import java.time.ZonedDateTime;
import java.util.List;

public abstract class InventoryEvent implements DomainEvent<InventoryItem> {
    private final InventoryItem inventoryItem;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessages;

    public InventoryEvent(InventoryItem inventoryItem, ZonedDateTime createdAt, List<String> failureMessages) {
        this.inventoryItem = inventoryItem;
        this.createdAt = createdAt;
        this.failureMessages = failureMessages;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}