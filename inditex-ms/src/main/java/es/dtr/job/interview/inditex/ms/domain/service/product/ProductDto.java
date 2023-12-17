package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.inditex.ms.domain.service.price.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private List<PriceDto> prices;
    private Instant createdOn;
    private Instant lastUpdatedOn;

}
