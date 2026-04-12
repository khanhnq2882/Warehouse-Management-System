package wms.product.application.service;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import wms.product.application.service.ports.output.message.publisher.inventory.CreateProductMessagePublisher;
import wms.product.application.service.ports.output.repository.CategoryRepository;
import wms.product.application.service.ports.output.repository.ProductRepository;
import wms.product.application.service.ports.output.repository.SupplierRepository;
import wms.product.domain.core.ProductDomainService;
import wms.product.domain.core.ProductDomainServiceImpl;

@SpringBootApplication(scanBasePackages = "wbs.product.application.service")
public class ProductTestConfiguration {

    @Bean
    public CreateProductMessagePublisher createProductMessagePublisher() {
        return Mockito.mock(CreateProductMessagePublisher.class);
    }

    @Bean
    public ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }

    @Bean
    public CategoryRepository categoryRepository() {
        return Mockito.mock(CategoryRepository.class);
    }

    @Bean
    public SupplierRepository supplierRepository() {
        return Mockito.mock(SupplierRepository.class);
    }

    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }

}
