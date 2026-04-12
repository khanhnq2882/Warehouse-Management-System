package wms.product.container;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wms.product.domain.core.ProductDomainService;
import wms.product.domain.core.ProductDomainServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }
}
