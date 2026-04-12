package wms.product.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.dto.CreateProductResponse;
import wms.product.application.service.mapper.ProductDataMapper;
import wms.product.application.service.ports.input.ProductApplicationService;
import wms.product.application.service.ports.output.message.publisher.inventory.CreateProductMessagePublisher;
import wms.product.domain.core.event.CreateProductEvent;

@Slf4j
@Component
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final ProductHelper productHelper;
    private final CreateProductMessagePublisher createProductMessagePublisher;
    private final ProductDataMapper productDataMapper;


    public ProductApplicationServiceImpl(ProductHelper productHelper,
                                         CreateProductMessagePublisher createProductMessagePublisher,
                                         ProductDataMapper productDataMapper) {
        this.productHelper = productHelper;
        this.createProductMessagePublisher = createProductMessagePublisher;
        this.productDataMapper = productDataMapper;
    }

    @Override
    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        CreateProductEvent createProductEvent = productHelper.persistProduct(createProductRequest);
        log.info("New product with id: {} is created", createProductEvent.getProduct().getId().getValue());
        createProductMessagePublisher.publish(createProductEvent);
        return productDataMapper.productToCreateProductResponse(createProductEvent.getProduct(), "New product created successfully!");
    }

}
