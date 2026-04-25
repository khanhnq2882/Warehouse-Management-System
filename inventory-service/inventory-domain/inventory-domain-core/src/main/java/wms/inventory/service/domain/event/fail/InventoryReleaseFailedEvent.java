package wms.inventory.service.domain.event.fail;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.List;

public class InventoryReleaseFailedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryReleaseFailedEvent> inventoryReleaseFailedEventPublisher;

    public InventoryReleaseFailedEvent(InventoryItem inventoryItem,
                                       ZonedDateTime createdAt,
                                       List<String> failureMessages,
                                       DomainEventPublisher<InventoryReleaseFailedEvent> inventoryReleaseFailedEventPublisher) {
        super(inventoryItem, createdAt, failureMessages);
        this.inventoryReleaseFailedEventPublisher = inventoryReleaseFailedEventPublisher;
    }

    @Override
    public void fire() {
        inventoryReleaseFailedEventPublisher.publish(this);
    }
}