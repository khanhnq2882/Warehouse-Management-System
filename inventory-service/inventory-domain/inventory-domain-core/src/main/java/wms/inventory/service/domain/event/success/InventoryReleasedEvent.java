package wms.inventory.service.domain.event.success;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.Collections;

public class InventoryReleasedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryReleasedEvent> inventoryReleasedEventDomainEventPublisher;

    public InventoryReleasedEvent(InventoryItem inventoryItem,
                                  ZonedDateTime createdAt,
                                  DomainEventPublisher<InventoryReleasedEvent> inventoryReleasedEventDomainEventPublisher) {
        super(inventoryItem, createdAt, Collections.emptyList());
        this.inventoryReleasedEventDomainEventPublisher = inventoryReleasedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        inventoryReleasedEventDomainEventPublisher.publish(this);
    }
}