package es.dtr.job.interview.inditex.ms.adapter.in.brand;

import es.dtr.job.interview.commons.api.crud.CrudController;
import es.dtr.job.interview.inditex.ms.configuration.OpenApiConfiguration;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(name = BrandControllerInterface.API_RESOURCE)
@RequestMapping(BrandControllerInterface.BASE_PATH)
public interface BrandControllerInterface extends CrudController<BrandDto, BrandEntity, Long> {

    // Paths
    String API_RESOURCE = "brands";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;

}
