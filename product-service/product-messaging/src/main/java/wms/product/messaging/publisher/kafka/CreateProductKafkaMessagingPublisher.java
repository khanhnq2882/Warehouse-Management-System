package wms.product.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wms.kafka.producer.KafkaMessageHelper;
import wms.kafka.producer.service.KafkaProducer;
import wms.kafka.product.avro.model.CreateProductAvroModel;
import wms.product.application.service.config.ProductServiceConfigData;
import wms.product.application.service.ports.output.message.publisher.inventory.CreateProductMessagePublisher;
import wms.product.messaging.mapper.ProductMessagingDataMapper;
import wms.product.domain.core.event.CreateProductEvent;

@Slf4j
@Component
public class CreateProductKafkaMessagingPublisher implements CreateProductMessagePublisher {
    private final KafkaProducer<String, CreateProductAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;
    private final ProductServiceConfigData productServiceConfigData;
    private final ProductMessagingDataMapper productMessagingDataMapper;

    public CreateProductKafkaMessagingPublisher(KafkaProducer<String, CreateProductAvroModel> kafkaProducer,
                                                KafkaMessageHelper kafkaMessageHelper,
                                                ProductServiceConfigData productServiceConfigData,
                                                ProductMessagingDataMapper productMessagingDataMapper) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaMessageHelper = kafkaMessageHelper;
        this.productServiceConfigData = productServiceConfigData;
        this.productMessagingDataMapper = productMessagingDataMapper;
    }

    @Override
    public void publish(CreateProductEvent createProductEvent) {
        String productId = createProductEvent.getProduct().getId().toString();
        log.info("Received CreateProductEvent for product id: {}", productId);
        try {
            CreateProductAvroModel createProductAvroModel = productMessagingDataMapper.createProductEventToCreateProductAvroModel(createProductEvent);
            kafkaProducer.send(
                    productServiceConfigData.getCreateProductTopicName(),
                    productId,
                    createProductAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                            productServiceConfigData.getCreateProductTopicName(),
                            createProductAvroModel,
                            productId,
                            "CreateProductAvroModel"
                    )
            );
        log.info("CreateProductAvroModel sent to Kafka for product id: {}", createProductAvroModel.getProductId());
        } catch (Exception e) {
            log.error("Error while sending CreateProductAvroModel message" +
                    " to kafka with product id: {}, error: {}", productId, e.getMessage());
        }
    }
}
