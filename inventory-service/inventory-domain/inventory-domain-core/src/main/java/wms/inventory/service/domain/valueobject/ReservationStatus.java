package wms.inventory.service.domain.valueobject;

public enum ReservationStatus {
    REQUESTED,

    /**
     * RESERVED:
     * - Items are reserved (held) for an order
     * - onHand: unchanged (physical stock remains the same)
     * - reserved: increases
     * - available = onHand - reserved → decreases
     * Example:
     * Warehouse has 8 items, customer reserves 3
     * → available stock becomes 5 for other customers
     */
    RESERVED,

    /**
     * DEDUCTED:
     * - Items have physically left the warehouse (picked/shipped successfully)
     * - onHand: decreases
     * - reserved: decreases
     * - available: unchanged (both onHand and reserved decrease equally)
     * Example:
     * Warehouse has 8 items, 3 were reserved and now shipped
     * → onHand = 5, reserved = 0, available = 5
     */
    DEDUCTED,

    /**
     * RELEASED:
     * - Reserved items are released (order cancelled or failed)
     * - onHand: unchanged
     * - reserved: decreases
     * - available = onHand - reserved → increases
     * Example:
     * Warehouse has 8 items, 3 were reserved but order is cancelled
     * → onHand = 8, reserved = 0, available = 8
     */
    RELEASED

    /**
     * State transitions:
     * RESERVED -> DEDUCTED
     * RESERVED -> RELEASED
     * DEDUCTED and RELEASED are terminal states (no further transitions allowed)
     */
}