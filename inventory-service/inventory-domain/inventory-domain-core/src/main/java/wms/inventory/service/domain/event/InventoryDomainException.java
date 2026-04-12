package wms.inventory.service.domain.event;

import wms.common.service.domain.exception.DomainException;

public class InventoryDomainException extends DomainException {
    public InventoryDomainException(String message) {
        super(message);
    }

    public InventoryDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}