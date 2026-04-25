package wms.inventory.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InventoryCommand {
    private String commandId;
    private String idempotencyKey;
    private String sagaId;
    private String orderId;
    private String productId;
    private String warehouseId;
    private Integer orderQuantity;
    private String inventoryAction; // RESERVE, DEDUCT, RELEASE
}