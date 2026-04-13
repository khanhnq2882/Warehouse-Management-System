package wms.inventory.service.domain.entity;

import wms.common.service.domain.entity.BaseEntity;
import wms.common.service.domain.valueobject.OrderId;
import wms.common.service.domain.valueobject.ProductId;
import wms.common.service.domain.valueobject.Quantity;
import wms.common.service.domain.valueobject.WarehouseId;
import wms.inventory.service.domain.valueobject.ReservationId;
import wms.inventory.service.domain.valueobject.ReservationStatus;
import java.time.LocalDateTime;

public class Reservation extends BaseEntity<ReservationId> {
    private final OrderId orderId;
    private final WarehouseId warehouseId;
    private final ProductId productId;
    private final Quantity quantity;
    private ReservationStatus reservationStatus;
    private LocalDateTime expiresAt;

    public boolean isReservationExpired() {
        return reservationStatus == ReservationStatus.RESERVED && expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }


    private Reservation(Builder builder) {
        super.setId(builder.reservationId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        orderId = builder.orderId;
        warehouseId = builder.warehouseId;
        productId = builder.productId;
        quantity = builder.quantity;
        reservationStatus = builder.reservationStatus;
        expiresAt = builder.expiresAt;
    }

    public static final class Builder {
        private ReservationId reservationId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private OrderId orderId;
        private WarehouseId warehouseId;
        private ProductId productId;
        private Quantity quantity;
        private ReservationStatus reservationStatus;
        private LocalDateTime expiresAt;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder reservationId(ReservationId val) {
            reservationId = val;
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

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder warehouseId(WarehouseId val) {
            warehouseId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder reservationStatus(ReservationStatus val) {
            reservationStatus = val;
            return this;
        }

        public Builder expiresAt(LocalDateTime val) {
            expiresAt = val;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
    }
}
