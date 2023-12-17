package es.dtr.job.interview.inditex.ms.adapter.in.price;

import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceDto;
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

    @Override
    public ResponseEntity<PriceDto> getWith(final Long productId, final Long brandId, final Instant date) {
        final PriceDto result = crudService.getWith(productId, brandId, date);
        return ResponseEntity.ok().body(result);
    }
}
