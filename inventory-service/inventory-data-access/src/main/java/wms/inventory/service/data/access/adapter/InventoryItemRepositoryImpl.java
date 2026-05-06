package wms.inventory.service.data.access.adapter;

import org.springframework.stereotype.Component;
import wms.inventory.service.data.access.entity.InventoryItemEntity;
import wms.inventory.service.data.access.mapper.InventoryItemDataAccessMapper;
import wms.inventory.service.data.access.repository.InventoryItemJpaRepository;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.ports.output.repository.InventoryItemRepository;
import java.util.Optional;
import java.util.UUID;

@Component
public class InventoryItemRepositoryImpl implements InventoryItemRepository {
    private final InventoryItemJpaRepository inventoryItemJpaRepository;
    private final InventoryItemDataAccessMapper inventoryItemDataAccessMapper;

    public InventoryItemRepositoryImpl(InventoryItemJpaRepository inventoryItemJpaRepository,
                                       InventoryItemDataAccessMapper inventoryItemDataAccessMapper) {
        this.inventoryItemJpaRepository = inventoryItemJpaRepository;
        this.inventoryItemDataAccessMapper = inventoryItemDataAccessMapper;
    }

    @Override
    public Optional<InventoryItem> findByProductIdAndWarehouseId(UUID productId, UUID warehouseId) {
        return Optional.empty();
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        InventoryItemEntity inventoryItemEntity = inventoryItemJpaRepository.save(
                inventoryItemDataAccessMapper.mapToInventoryItemEntity(inventoryItem)
        );
        return inventoryItemDataAccessMapper.mapToInventoryItem(inventoryItemEntity);
    }
}