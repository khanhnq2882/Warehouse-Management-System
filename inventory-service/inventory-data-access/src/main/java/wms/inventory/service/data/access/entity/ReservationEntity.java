package wms.inventory.service.data.access.entity;

import lombok.*;
import wms.inventory.service.domain.valueobject.ReservationStatus;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
@Entity
public class ReservationEntity {

    @Id
    @Column(name = "RESERVATION_ID")
    private UUID reservationId;

    @Column(name = "ORDER_ID")
    private UUID orderId;

    @Column(name = "WAREHOUSE_ID")
    private UUID warehouseId;

    @Column(name = "PRODUCT_ID")
    private UUID productId;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "RESERVATION_STATUS")
    private ReservationStatus reservationStatus;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_ITEM_ID", nullable = false)
    private InventoryItemEntity inventoryItem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
}