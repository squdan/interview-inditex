package es.dtr.job.interview.inditex.ms.application.rest.product;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudController;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(name = ProductControllerInterface.API_RESOURCE)
@RequestMapping(ProductControllerInterface.BASE_PATH)
public interface ProductControllerInterface extends CrudController<ProductDto, ProductEntity, Long> {

    // Paths
    String API_RESOURCE = "products";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;

}
