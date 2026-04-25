package wms.inventory.service.domain.event.fail;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.List;

public class InventoryReservationFailedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryReservationFailedEvent> inventoryReservationFailedEventDomainEventPublisher;

    public InventoryReservationFailedEvent(InventoryItem inventoryItem,
                                           ZonedDateTime createdAt,
                                           List<String> failureMessages,
                                           DomainEventPublisher<InventoryReservationFailedEvent> inventoryReservationFailedEventDomainEventPublisher) {
        super(inventoryItem, createdAt, failureMessages);
        this.inventoryReservationFailedEventDomainEventPublisher = inventoryReservationFailedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        inventoryReservationFailedEventDomainEventPublisher.publish(this);
    }
}