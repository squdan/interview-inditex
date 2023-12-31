package es.dtr.job.interview.inditex.ms.application.rest.product;

import es.dtr.job.interview.inditex.ms.domain.service.product.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerInterface {

    // Dependencies
    private final Class<ProductController> crudController = ProductController.class;
    private final ProductService crudService;
    private final ProductControllerMapper mapper;

}
