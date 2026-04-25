package wms.inventory.service.domain.ports.output.message.publisher;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.inventory.service.domain.event.fail.InventoryReleaseFailedEvent;

public interface InventoryReleaseFailedPublisher extends DomainEventPublisher<InventoryReleaseFailedEvent> {
}