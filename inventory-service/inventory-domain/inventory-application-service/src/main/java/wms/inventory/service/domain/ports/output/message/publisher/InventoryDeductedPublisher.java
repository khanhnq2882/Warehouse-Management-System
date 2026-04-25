package wms.inventory.service.domain.ports.output.message.publisher;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.event.success.InventoryDeductedEvent;

public interface InventoryDeductedPublisher extends DomainEventPublisher<InventoryDeductedEvent> {
}