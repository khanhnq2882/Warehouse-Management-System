package wms.inventory.service.data.access.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_items")
@Entity
public class InventoryItemEntity {
    @Id
    @Column(name = "INVENTORY_ITEM_ID")
    private UUID inventoryItemId;

    @Column(name = "PRODUCT_ID")
    private UUID productId;

    @Column(name = "WAREHOUSE_ID")
    private UUID warehouseId;

    @Column(name = "ON_HAND_QUANTITY")
    private Integer onHandQuantity;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItemEntity that = (InventoryItemEntity) o;
        return Objects.equals(inventoryItemId, that.inventoryItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryItemId);
    }
}