package es.dtr.job.interview.inditex.ms.application.rest.price;

import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Getter
@RestController
@RequiredArgsConstructor
public class PriceController implements PriceControllerInterface {

    // Dependencies
    private final Class<PriceController> crudController = PriceController.class;
    private final PriceService crudService;
    private final PriceControllerMapper mapper;

    @Override
    public ResponseEntity<EntityModel<PriceDto>> getWithProductBrandDate(final Long productId, final Long brandId, final Instant date) {
        final PriceEntity element = crudService.getWith(productId, brandId, date);
        final PriceDto result = mapper.domainEntityToDto(element);
        return ResponseEntity.ok(EntityModel.of(result, getHateoas(result.getId())));
    }
}
