package wms.inventory.service.domain.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.service.domain.valueobject.ProductId;
import wms.common.service.domain.valueobject.Quantity;
import wms.common.service.domain.valueobject.WarehouseId;
import wms.inventory.service.domain.valueobject.InventoryItemId;
import java.time.LocalDateTime;
import java.util.List;

public class InventoryItem extends AggregateRoot<InventoryItemId> {
    private final ProductId productId;
    private final WarehouseId warehouseId;
    private Quantity availableQuantity;
    private List<Reservation> reservations;
    private int version;

    private InventoryItem(Builder builder) {
        super.setId(builder.inventoryItemId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        productId = builder.productId;
        warehouseId = builder.warehouseId;
        availableQuantity = builder.availableQuantity;
        reservations = builder.reservations;
        version = builder.version;
    }

    public static final class Builder {
        private InventoryItemId inventoryItemId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private ProductId productId;
        private WarehouseId warehouseId;
        private Quantity availableQuantity;
        private List<Reservation> reservations;
        private int version;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder inventoryItemId(InventoryItemId val) {
            inventoryItemId = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder warehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder availableQuantity(Quantity val) {
            availableQuantity = val;
            return this;
        }

        public Builder reservations(List<Reservation> val) {
            reservations = val;
            return this;
        }

        public Builder version(int val) {
            version = val;
            return this;
        }


        public InventoryItem build() {
            return new InventoryItem(this);
        }
    }
}
