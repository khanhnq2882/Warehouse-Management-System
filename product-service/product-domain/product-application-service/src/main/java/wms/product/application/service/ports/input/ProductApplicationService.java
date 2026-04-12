package wms.product.application.service.ports.input;

import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.dto.CreateProductResponse;

import javax.validation.Valid;

public interface ProductApplicationService {
    CreateProductResponse createProduct(@Valid CreateProductRequest createProductRequest);
}
