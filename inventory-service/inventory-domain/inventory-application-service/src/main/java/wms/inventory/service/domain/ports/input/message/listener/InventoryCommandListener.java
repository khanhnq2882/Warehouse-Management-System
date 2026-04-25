package wms.inventory.service.domain.ports.input.message.listener;

import wms.inventory.service.domain.dto.InventoryCommand;

public interface InventoryCommandListener {
    void reserveInventory(InventoryCommand inventoryCommand);

    void deductInventory(InventoryCommand inventoryCommand);

    void releaseInventory(InventoryCommand inventoryCommand);
}