package wms.inventory.service.domain.event.success;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.Collections;

public class InventoryReservedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryReservedEvent> inventoryReservedEventDomainEventPublisher;

    public InventoryReservedEvent(InventoryItem inventoryItem,
                                  ZonedDateTime createdAt,
                                  DomainEventPublisher<InventoryReservedEvent> inventoryReservedEventDomainEventPublisher) {
        super(inventoryItem, createdAt, Collections.emptyList());
        this.inventoryReservedEventDomainEventPublisher = inventoryReservedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        inventoryReservedEventDomainEventPublisher.publish(this);
    }
}