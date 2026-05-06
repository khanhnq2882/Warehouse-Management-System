package wms.inventory.service.data.access.mapper;

import org.springframework.stereotype.Component;
import wms.common.service.domain.valueobject.OrderId;
import wms.common.service.domain.valueobject.ProductId;
import wms.common.service.domain.valueobject.Quantity;
import wms.common.service.domain.valueobject.WarehouseId;
import wms.inventory.service.data.access.entity.InventoryItemEntity;
import wms.inventory.service.data.access.entity.ReservationEntity;
import wms.inventory.service.domain.entity.InventoryItem;
import wms.inventory.service.domain.entity.Reservation;
import wms.inventory.service.domain.valueobject.InventoryItemId;
import wms.inventory.service.domain.valueobject.ReservationId;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class InventoryItemDataAccessMapper {

    public InventoryItemEntity mapToInventoryItemEntity(InventoryItem inventoryItem) {
        return InventoryItemEntity.builder()
                .inventoryItemId(inventoryItem.getId().getValue())
                .warehouseId(inventoryItem.getWarehouseId().getValue())
                .productId(inventoryItem.getProductId().getValue())
                .onHandQuantity(inventoryItem.getOnHandQuantity().quantity())
                .reservations(mapToReservationEntities(inventoryItem.getReservations()))
                .build();
    }

    public InventoryItem mapToInventoryItem(InventoryItemEntity inventoryItemEntity) {
        return InventoryItem.Builder.builder()
                .inventoryItemId(new InventoryItemId(inventoryItemEntity.getInventoryItemId()))
                .productId(new ProductId(inventoryItemEntity.getProductId()))
                .warehouseId(new WarehouseId(inventoryItemEntity.getWarehouseId()))
                .onHandQuantity(new Quantity(inventoryItemEntity.getOnHandQuantity()))
                .reservations(mapToReservations(inventoryItemEntity.getReservations()))
                .build();
    }

    private List<ReservationEntity> mapToReservationEntities(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> ReservationEntity.builder()
                        .reservationId(reservation.getId().getValue())
                        .orderId(reservation.getOrderId().getValue())
                        .warehouseId(reservation.getWarehouseId().getValue())
                        .productId(reservation.getProductId().getValue())
                        .quantity(reservation.getQuantity().quantity())
                        .reservationStatus(reservation.getReservationStatus())
                        .expiresAt(reservation.getExpiresAt())
                        .build())
                .toList();
    }

    private List<Reservation> mapToReservations(List<ReservationEntity> reservationEntities) {
        return reservationEntities.stream()
                .map(reservationEntity -> Reservation.Builder.builder()
                        .reservationId(new ReservationId(reservationEntity.getReservationId()))
                        .orderId(new OrderId(reservationEntity.getOrderId()))
                        .warehouseId(new WarehouseId(reservationEntity.getWarehouseId()))
                        .productId(new ProductId(reservationEntity.getProductId()))
                        .quantity(new Quantity(reservationEntity.getQuantity()))
                        .reservationStatus(reservationEntity.getReservationStatus())
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();
    }
}