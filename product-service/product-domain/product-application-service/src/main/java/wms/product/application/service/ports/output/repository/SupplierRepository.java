package wms.product.application.service.ports.output.repository;

import wms.product.domain.core.entity.Supplier;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository{
    Optional<Supplier> findSupplier(UUID supplierId);
}
