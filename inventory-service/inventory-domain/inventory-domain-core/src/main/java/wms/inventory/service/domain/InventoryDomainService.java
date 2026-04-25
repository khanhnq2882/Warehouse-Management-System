package wms.inventory.service.domain;

import wms.common.service.domain.event.publisher.DomainEventPublisher;
import wms.common.service.domain.valueobject.OrderId;
import wms.common.service.domain.valueobject.Quantity;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import wms.inventory.service.domain.event.fail.InventoryDeductionFailedEvent;
import wms.inventory.service.domain.event.fail.InventoryReleaseFailedEvent;
import wms.inventory.service.domain.event.fail.InventoryReservationFailedEvent;
import wms.inventory.service.domain.event.success.InventoryDeductedEvent;
import wms.inventory.service.domain.event.success.InventoryReleasedEvent;
import wms.inventory.service.domain.event.success.InventoryReservedEvent;
import java.util.List;

public interface InventoryDomainService {
    InventoryEvent reserveInventory(InventoryItem inventoryItem,
                                    OrderId orderId,
                                    Quantity orderQuantity,
                                    List<String> failureMessages,
                                    DomainEventPublisher<InventoryReservedEvent> eventPublisher,
                                    DomainEventPublisher<InventoryReservationFailedEvent> inventoryReservationFailedEventPublisher);

    InventoryEvent deductInventory(InventoryItem inventoryItem,
                                   OrderId orderId,
                                   List<String> failureMessages,
                                   DomainEventPublisher<InventoryDeductedEvent> eventPublisher,
                                   DomainEventPublisher<InventoryDeductionFailedEvent> inventoryDeductionFailedEventEventPublisher);

    InventoryEvent releaseInventory(InventoryItem inventoryItem,
                                    OrderId orderId,
                                    List<String> failureMessages,
                                    DomainEventPublisher<InventoryReleasedEvent> eventPublisher,
                                    DomainEventPublisher<InventoryReleaseFailedEvent> inventoryReleaseFailedEventEventPublisher);
}