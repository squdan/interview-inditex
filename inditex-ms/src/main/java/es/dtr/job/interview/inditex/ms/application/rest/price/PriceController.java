package es.dtr.job.interview.inditex.ms.application.rest.price;

import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Getter
@RestController
@RequiredArgsConstructor
public class PriceController implements PriceControllerInterface {

    // Dependencies
    private final PriceService crudService;
    private final PriceControllerMapper mapper;

    @Override
    public ResponseEntity<PriceDto> getWith(final Long productId, final Long brandId, final Instant date) {
        final PriceEntity element = crudService.getWith(productId, brandId, date);
        final PriceDto result = mapper.domainEntityToDto(element);
        return ResponseEntity.ok().body(result);
    }
}
