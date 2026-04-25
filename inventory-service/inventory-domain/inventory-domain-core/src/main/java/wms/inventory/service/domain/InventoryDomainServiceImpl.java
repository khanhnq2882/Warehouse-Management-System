package wms.inventory.service.domain;

import lombok.extern.slf4j.Slf4j;
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
import wms.inventory.service.domain.exception.InventoryDomainException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import static wms.common.service.domain.DomainConstants.UTC;

@Slf4j
public class InventoryDomainServiceImpl implements InventoryDomainService {

    @Override
    public InventoryEvent reserveInventory(InventoryItem inventoryItem,
                                           OrderId orderId,
                                           Quantity orderQuantity,
                                           List<String> failureMessages,
                                           DomainEventPublisher<InventoryReservedEvent>
                                                       inventoryReservedEventEventPublisher,
                                           DomainEventPublisher<InventoryReservationFailedEvent>
                                                       inventoryReservationFailedEventPublisher) {
        try {
            inventoryItem.reserve(orderId, orderQuantity);
            log.info("Inventory reserved successfully for orderId={}, productId={}, warehouseId={}, quantity={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId(),
                    orderQuantity);
            return new InventoryReservedEvent(
                    inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), inventoryReservedEventEventPublisher);
        } catch (InventoryDomainException exception) {
            failureMessages.add(exception.getMessage());
            log.error("Inventory reservation failed for orderId={}, productId={}, warehouseId={}, quantity={}, error={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId(),
                    orderQuantity,
                    exception.getMessage());
            return new InventoryReservationFailedEvent(
                    inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages, inventoryReservationFailedEventPublisher);
        }
    }

    @Override
    public InventoryEvent deductInventory(InventoryItem inventoryItem,
                                          OrderId orderId,
                                          List<String> failureMessages,
                                          DomainEventPublisher<InventoryDeductedEvent>
                                                      inventoryDeductedEventEventPublisher,
                                          DomainEventPublisher<InventoryDeductionFailedEvent>
                                                      inventoryDeductionFailedEventEventPublisher) {
        try {
            inventoryItem.deduct(orderId);
            log.info("Inventory deducted successfully for orderId={}, productId={}, warehouseId={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId());
            return new InventoryDeductedEvent(
                    inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), inventoryDeductedEventEventPublisher
            );
        } catch (InventoryDomainException exception) {
            failureMessages.add(exception.getMessage());
            log.error("Inventory deduction failed for orderId={}, productId={}, warehouseId={}, error={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId(),
                    exception.getMessage());
            return new InventoryDeductionFailedEvent(
                    inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages, inventoryDeductionFailedEventEventPublisher);
        }
    }

    @Override
    public InventoryEvent releaseInventory(InventoryItem inventoryItem,
                                           OrderId orderId,
                                           List<String> failureMessages,
                                           DomainEventPublisher<InventoryReleasedEvent>
                                                       inventoryReleasedEventEventPublisher,
                                           DomainEventPublisher<InventoryReleaseFailedEvent>
                                                       inventoryReleaseFailedEventEventPublisher) {
        try {
            inventoryItem.release(orderId);
            log.info("Inventory released successfully for orderId={}, productId={}, warehouseId={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId());
            return new InventoryReleasedEvent(inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), inventoryReleasedEventEventPublisher);
        } catch (InventoryDomainException exception) {
            failureMessages.add(exception.getMessage());
            log.error("Inventory release failed for orderId={}, productId={}, warehouseId={}, error={}",
                    orderId,
                    inventoryItem.getProductId(),
                    inventoryItem.getWarehouseId(),
                    exception.getMessage());
            return new InventoryReleaseFailedEvent(
                    inventoryItem, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages, inventoryReleaseFailedEventEventPublisher);
        }
    }

}