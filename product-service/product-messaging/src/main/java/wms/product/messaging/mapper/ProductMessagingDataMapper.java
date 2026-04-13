package wms.product.messaging.mapper;

import org.springframework.stereotype.Component;
import wms.kafka.product.avro.model.CreateProductAvroModel;
import wms.kafka.product.avro.model.ProductStatus;
import wms.product.domain.core.entity.Product;
import wms.product.domain.core.event.CreateProductEvent;

@Component
public class ProductMessagingDataMapper {

    public CreateProductAvroModel createProductEventToCreateProductAvroModel(CreateProductEvent createProductEvent) {
        Product product = createProductEvent.getProduct();
        return CreateProductAvroModel.newBuilder()
                .setProductId(product.getId().getValue().toString())
                .setSku(product.getSku())
                .setProductName(product.getProductName())
                .setProductDescription(product.getProductDescription())
                .setProductPrice(product.getProductPrice().amount())
                .setCategoryId(product.getCategoryId().getValue().toString())
                .setProductStatus(ProductStatus.valueOf(product.getProductStatus().name()))
                .build();
    }
}
