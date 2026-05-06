package wms.inventory.service.data.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wms.inventory.service.data.access.entity.InventoryItemEntity;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemJpaRepository extends JpaRepository<InventoryItemEntity, UUID> {
    Optional<InventoryItemEntity> findByProductIdAndWarehouseId(UUID productId, UUID warehouseId);
}