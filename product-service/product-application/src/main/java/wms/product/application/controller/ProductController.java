package wms.product.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wms.product.application.service.dto.CreateProductRequest;
import wms.product.application.service.dto.CreateProductResponse;
import wms.product.application.service.ports.input.ProductApplicationService;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = "application/vnd.api.v1+json")
public class ProductController {
    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(productApplicationService.createProduct(createProductRequest));
    }


}
