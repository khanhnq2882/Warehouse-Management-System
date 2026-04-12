package wms.product.domain.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wms.product.domain.core.entity.Product;
import wms.product.domain.core.event.CreateProductEvent;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@AllArgsConstructor
public class ProductDomainServiceImpl implements ProductDomainService {
    private static final String UTC = "UTC";

    @Override
    public CreateProductEvent createProduct(Product product) {
        product.createProduct();
        log.info("New product with id: {} is created", product.getId().getValue());
        return new CreateProductEvent(product, ZonedDateTime.now(ZoneId.of(UTC)));
    }

}
