package wms.inventory.service.domain.event.success;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.Collections;

public class InventoryDeductedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryDeductedEvent> inventoryDeductedEventDomainEventPublisher;

    public InventoryDeductedEvent(InventoryItem inventoryItem,
                                  ZonedDateTime createdAt,
                                  DomainEventPublisher<InventoryDeductedEvent> inventoryDeductedEventDomainEventPublisher) {
        super(inventoryItem, createdAt, Collections.emptyList());
        this.inventoryDeductedEventDomainEventPublisher = inventoryDeductedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        inventoryDeductedEventDomainEventPublisher.publish(this);
    }
}