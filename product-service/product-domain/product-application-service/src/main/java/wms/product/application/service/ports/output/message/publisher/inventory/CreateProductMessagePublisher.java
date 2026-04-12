package wms.product.application.service.ports.output.message.publisher.inventory;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.product.domain.core.event.CreateProductEvent;

public interface CreateProductMessagePublisher extends DomainEventPublisher<CreateProductEvent> {
}
