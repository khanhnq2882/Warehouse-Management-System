package wms.inventory.service.domain.event.fail;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import java.time.ZonedDateTime;
import java.util.List;

public class InventoryDeductionFailedEvent extends InventoryEvent {

    private final DomainEventPublisher<InventoryDeductionFailedEvent>  inventoryDeductionFailedEventPublisher;

    public InventoryDeductionFailedEvent(InventoryItem inventoryItem,
                                         ZonedDateTime createdAt,
                                         List<String> failureMessages,
                                         DomainEventPublisher<InventoryDeductionFailedEvent>  inventoryDeductionFailedEventPublisher) {
        super(inventoryItem, createdAt, failureMessages);
        this.inventoryDeductionFailedEventPublisher = inventoryDeductionFailedEventPublisher;
    }

    @Override
    public void fire() {
        inventoryDeductionFailedEventPublisher.publish(this);
    }
}