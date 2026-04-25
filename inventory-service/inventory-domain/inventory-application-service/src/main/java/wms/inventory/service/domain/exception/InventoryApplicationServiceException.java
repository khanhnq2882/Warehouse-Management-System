package wms.inventory.service.domain.exception;

import wms.common.service.domain.exception.DomainException;

public class InventoryApplicationServiceException extends DomainException {
    public InventoryApplicationServiceException(String message) {
        super(message);
    }
}