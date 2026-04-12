package wms.inventory.service.domain.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.service.domain.valueobject.*;
import wms.inventory.service.domain.event.InventoryDomainException;
import wms.inventory.service.domain.valueobject.InventoryItemId;
import wms.inventory.service.domain.valueobject.ReservationId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class InventoryItem extends AggregateRoot<InventoryItemId> {
    private final ProductId productId;
    private final WarehouseId warehouseId;

    // Tổng số hàng thực tế trong kho
    private Quantity onHandQuantity;
    private List<Reservation> reservations;
    private int version;

    public void reverse(OrderId orderId, Quantity orderQuantity) {
        Reservation existReservation = findReservation(orderId);
        if (Objects.nonNull(existReservation)) {
            if (existReservation.getReservationStatus() == ReservationStatus.RESERVED) return;
            if (existReservation.getReservationStatus() == ReservationStatus.DEDUCTED) {
                throw new InventoryDomainException("Already deducted, cannot reserve again.");
            }
        }
        Quantity availableQuantity = onHandQuantity.subtract(totalReserved());
        if (orderQuantity.isGreaterThan(availableQuantity)) {
            throw new InventoryDomainException("Out of stock.");
        }
        Reservation reservation = Reservation.Builder.builder()
                .reservationId(new ReservationId(UUID.randomUUID()))
                .orderId(orderId)
                .warehouseId(warehouseId)
                .productId(productId)
                .quantity(orderQuantity)
                .reservationStatus(ReservationStatus.RESERVED)
                .createdAt(LocalDateTime.now())
                .build();
        reservations.add(reservation);
    }

    public void release(OrderId orderId) {
        Reservation reservation = findReservation(orderId);
        if (reservation == null) return;
        if (reservation.getReservationStatus() == ReservationStatus.RELEASED) return;
        if (reservation.getReservationStatus() == ReservationStatus.DEDUCTED) {
            throw new IllegalStateException("Cannot release deducted reservation");
        }
        reservation.setReservationStatus(ReservationStatus.RELEASED);
    }

    public void deduct(OrderId orderId, Quantity orderQuantity) {
        Reservation reservation = findReservation(orderId);
        if (reservation == null) throw new IllegalStateException("Reservation not found");
        if (reservation.getReservationStatus() == ReservationStatus.DEDUCTED) return;
        if (reservation.getReservationStatus() == ReservationStatus.RELEASED) {
            throw new IllegalStateException("Cannot deduct released reservation");
        }
        if (reservation.getQuantity().isGreaterThan(orderQuantity)) {
            throw new IllegalStateException("Invalid state: not enough onHand");
        }
        onHandQuantity = onHandQuantity.subtract(reservation.getQuantity());
        reservation.setReservationStatus(ReservationStatus.DEDUCTED);
    }

    private Quantity totalReserved() {
        return reservations.stream()
                .filter(r -> r.getReservationStatus() == ReservationStatus.RESERVED)
                .map(Reservation::getQuantity)
                .reduce(Quantity.ZERO, Quantity::add);
    }

    private Reservation findReservation(OrderId orderId) {
        return reservations.stream()
                .filter(r -> r.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public ProductId getProductId() {
        return productId;
    }

    public WarehouseId getWarehouseId() {
        return warehouseId;
    }

    public Quantity getOnHandQuantity() {
        return onHandQuantity;
    }

    public void setOnHandQuantity(Quantity onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private InventoryItem(Builder builder) {
        super.setId(builder.inventoryItemId);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        productId = builder.productId;
        warehouseId = builder.warehouseId;
        onHandQuantity = builder.onHandQuantity;
        reservations = builder.reservations;
        version = builder.version;
    }

    public static final class Builder {
        private InventoryItemId inventoryItemId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private ProductId productId;
        private WarehouseId warehouseId;
        private Quantity onHandQuantity;
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

        public Builder onHandQuantity(Quantity val) {
            onHandQuantity = val;
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
