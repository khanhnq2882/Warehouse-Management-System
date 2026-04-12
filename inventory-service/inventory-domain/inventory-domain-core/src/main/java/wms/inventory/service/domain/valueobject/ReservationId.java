package wms.inventory.service.domain.valueobject;

import wms.common.service.domain.valueobject.BaseId;
import java.util.UUID;

public class ReservationId extends BaseId<UUID> {
    public ReservationId(UUID value) {
        super(value);
    }
}
