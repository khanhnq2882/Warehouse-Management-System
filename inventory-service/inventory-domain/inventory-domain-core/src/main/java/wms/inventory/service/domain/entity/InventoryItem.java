package wms.inventory.service.domain.entity;

import wms.common.service.domain.entity.AggregateRoot;
import wms.common.service.domain.valueobject.*;
import wms.inventory.service.domain.exception.InventoryDomainException;
import wms.inventory.service.domain.valueobject.InventoryItemId;
import wms.inventory.service.domain.valueobject.ReservationId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import wms.inventory.service.domain.valueobject.ReservationStatus;

public class InventoryItem extends AggregateRoot<InventoryItemId> {
    private final ProductId productId;
    private final WarehouseId warehouseId;
    private Quantity onHandQuantity;
    private List<Reservation> reservations;
    private int version;

    public void reserve(OrderId orderId, Quantity orderQuantity) {
        Reservation existReservation = findReservationByOrderId(orderId);
        if (Objects.nonNull(existReservation)) {
            if (existReservation.getReservationStatus() == ReservationStatus.RESERVED) return;
            if (existReservation.getReservationStatus() == ReservationStatus.DEDUCTED) {
                throw new InventoryDomainException("Already deducted, cannot reserve again.");
            }
            if (existReservation.getReservationStatus() == ReservationStatus.RELEASED) {
                throw new InventoryDomainException("Already released, cannot reserve again.");
            }
        }
        Quantity totalReservedQuantity = reservations.stream()
                .filter(reservation -> reservation.getReservationStatus() == ReservationStatus.RESERVED)
                .map(Reservation::getQuantity)
                .reduce(Quantity.ZERO, Quantity::add);
        Quantity availableQuantity = onHandQuantity.subtract(totalReservedQuantity);
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
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        reservations.add(reservation);
    }

    public void deduct(OrderId orderId) {
        Reservation orderReservation = findReservationByOrderId(orderId);
        if (orderReservation == null) throw new InventoryDomainException("Reservation not found");
        if (orderReservation.isReservationExpired()) throw new InventoryDomainException("Reservation is expired");
        if (orderReservation.getReservationStatus() == ReservationStatus.DEDUCTED) return;
        if (orderReservation.getReservationStatus() == ReservationStatus.RELEASED) {
            throw new InventoryDomainException("Cannot deduct released reservation");
        }
        if (onHandQuantity.isLessThan(orderReservation.getQuantity())) {
            throw new InventoryDomainException("Invalid state: not enough onHand");
        }
        if (orderReservation.getReservationStatus() == ReservationStatus.RESERVED) {
            onHandQuantity = onHandQuantity.subtract(orderReservation.getQuantity());
            orderReservation.setReservationStatus(ReservationStatus.DEDUCTED);
        }
    }

    public void release(OrderId orderId) {
        Reservation orderReservation = findReservationByOrderId(orderId);
        if (orderReservation == null) return;
        if (orderReservation.getReservationStatus() == ReservationStatus.RELEASED) return;
        if (orderReservation.getReservationStatus() == ReservationStatus.DEDUCTED) {
            throw new InventoryDomainException("Cannot release deducted reservation");
        }
        if (orderReservation.getReservationStatus() == ReservationStatus.RESERVED) {
            orderReservation.setReservationStatus(ReservationStatus.RELEASED);
        }
    }

    private Reservation findReservationByOrderId(OrderId orderId) {
        return reservations.stream()
                .filter(reservation -> reservation.getOrderId().equals(orderId))
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
