package es.dtr.job.interview.inditex.ms.adapter.in.product;

import es.dtr.job.interview.commons.api.crud.CrudController;
import es.dtr.job.interview.inditex.ms.configuration.OpenApiConfiguration;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
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
