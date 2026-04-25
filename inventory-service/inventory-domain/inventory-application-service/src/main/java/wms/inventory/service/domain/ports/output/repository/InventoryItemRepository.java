package wms.inventory.service.domain.ports.output.repository;

import wms.inventory.service.domain.entity.InventoryItem;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemRepository {
    Optional<InventoryItem> findInventoryItemByProductIdAndWarehouseId(UUID productId, UUID warehouseId);
    InventoryItem save(InventoryItem inventoryItem);
}