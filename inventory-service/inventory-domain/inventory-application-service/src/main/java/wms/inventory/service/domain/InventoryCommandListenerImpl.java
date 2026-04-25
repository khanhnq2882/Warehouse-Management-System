package wms.inventory.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wms.common.service.domain.valueobject.InventoryAction;
import wms.common.service.domain.valueobject.OrderId;
import wms.common.service.domain.valueobject.Quantity;
import wms.inventory.service.domain.dto.InventoryCommand;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.event.InventoryEvent;
import wms.inventory.service.domain.exception.InventoryApplicationServiceException;
import wms.inventory.service.domain.ports.input.message.listener.InventoryCommandListener;
import wms.inventory.service.domain.ports.output.message.publisher.*;
import wms.inventory.service.domain.ports.output.repository.InventoryItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class InventoryCommandListenerImpl implements InventoryCommandListener {

    private final InventoryDomainService inventoryDomainService;
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryReservedPublisher inventoryReservedPublisher;
    private final InventoryReservationFailedPublisher inventoryReservationFailedPublisher;
    private final InventoryDeductedPublisher inventoryDeductedPublisher;
    private final InventoryDeductionFailedPublisher inventoryDeductionFailedPublisher;
    private final InventoryReleasedPublisher inventoryReleasedPublisher;
    private final InventoryReleaseFailedPublisher inventoryReleaseFailedPublisher;

    public InventoryCommandListenerImpl(InventoryDomainService inventoryDomainService,
                                  InventoryItemRepository inventoryItemRepository,
                                  InventoryReservedPublisher inventoryReservedPublisher,
                                  InventoryReservationFailedPublisher inventoryReservationFailedPublisher,
                                  InventoryDeductedPublisher inventoryDeductedPublisher,
                                  InventoryDeductionFailedPublisher inventoryDeductionFailedPublisher,
                                  InventoryReleasedPublisher inventoryReleasedPublisher,
                                  InventoryReleaseFailedPublisher inventoryReleaseFailedPublisher) {
        this.inventoryDomainService = inventoryDomainService;
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryReservedPublisher = inventoryReservedPublisher;
        this.inventoryReservationFailedPublisher = inventoryReservationFailedPublisher;
        this.inventoryDeductedPublisher = inventoryDeductedPublisher;
        this.inventoryDeductionFailedPublisher = inventoryDeductionFailedPublisher;
        this.inventoryReleasedPublisher = inventoryReleasedPublisher;
        this.inventoryReleaseFailedPublisher = inventoryReleaseFailedPublisher;
    }

    @Override
    public void reserveInventory(InventoryCommand inventoryCommand) {
        InventoryEvent inventoryEvent = persistActionInventory(inventoryCommand);
        fireEvent(inventoryEvent);
    }

    @Override
    public void deductInventory(InventoryCommand inventoryCommand) {
        InventoryEvent inventoryEvent = persistActionInventory(inventoryCommand);
        fireEvent(inventoryEvent);
    }

    @Override
    public void releaseInventory(InventoryCommand inventoryCommand) {
        InventoryEvent inventoryEvent = persistActionInventory(inventoryCommand);
        fireEvent(inventoryEvent);
    }

    @Transactional
    public InventoryEvent persistActionInventory(InventoryCommand inventoryCommand) {
        InventoryItem existInventoryItem = getExistInventoryItem(inventoryCommand.getProductId(), inventoryCommand.getWarehouseId());
        OrderId orderId = new OrderId(UUID.fromString(inventoryCommand.getOrderId()));
        Quantity orderQuantity = new Quantity(inventoryCommand.getOrderQuantity());
        String inventoryAction = inventoryCommand.getInventoryAction();
        List<String> failureMessages = new ArrayList<>();
        if (InventoryAction.RESERVE.name().equalsIgnoreCase(inventoryAction)) {
            InventoryEvent inventoryReservedEvent = inventoryDomainService.reserveInventory(
                    existInventoryItem, orderId, orderQuantity, failureMessages, inventoryReservedPublisher, inventoryReservationFailedPublisher
            );
            inventoryItemRepository.save(existInventoryItem);
            log.info("Inventory is reserved with id: {}", inventoryReservedEvent.getInventoryItem().getId().getValue());
            return inventoryReservedEvent;
        } else if (InventoryAction.DEDUCT.name().equalsIgnoreCase(inventoryAction)) {
            InventoryEvent inventoryDeductedEvent = inventoryDomainService.deductInventory(
                    existInventoryItem, orderId, failureMessages, inventoryDeductedPublisher, inventoryDeductionFailedPublisher
            );
            inventoryItemRepository.save(existInventoryItem);
            log.info("Inventory is deducted with id: {}", inventoryDeductedEvent.getInventoryItem().getId().getValue());
            return inventoryDeductedEvent;
        } else if (InventoryAction.RELEASE.name().equalsIgnoreCase(inventoryAction)) {
            InventoryEvent inventoryReleasedEvent = inventoryDomainService.releaseInventory(
                    existInventoryItem, orderId, failureMessages, inventoryReleasedPublisher, inventoryReleaseFailedPublisher
            );
            inventoryItemRepository.save(existInventoryItem);
            log.info("Inventory is released with id: {}", inventoryReleasedEvent.getInventoryItem().getId().getValue());
            return inventoryReleasedEvent;
        } else {
            log.error("Invalid inventory action. Inventory action must be RESERVE, DEDUCT, RELEASE.");
            throw new InventoryApplicationServiceException("Invalid inventory action. Inventory action must be RESERVE, DEDUCT, RELEASE.");
        }
    }

    private InventoryItem getExistInventoryItem (String productId, String warehouseId) {
        Optional<InventoryItem> existInventoryItem = inventoryItemRepository.findInventoryItemByProductIdAndWarehouseId(
                UUID.fromString(productId),
                UUID.fromString(warehouseId)
        );
        if (existInventoryItem.isEmpty()) {
            log.error("Inventory item not found for productId = {} and warehouseId = {}", productId, warehouseId);
            throw new InventoryApplicationServiceException("Inventory item not found for productId = " + productId + " and warehouseId = " + warehouseId);
        }
        return existInventoryItem.get();
    }

    private void fireEvent(InventoryEvent inventoryEvent) {
        log.info("Publishing inventory event with inventory item id: {}", inventoryEvent.getInventoryItem().getId().getValue());
        inventoryEvent.fire();
    }
}