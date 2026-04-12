package wms.common.service.domain.event.publisher;

import wms.common.service.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
