package wms.inventory.service.domain.exception;

import wms.common.service.domain.exception.DomainException;

public class InventoryDomainException extends DomainException {
    public InventoryDomainException(String message) {
        super(message);
    }
}