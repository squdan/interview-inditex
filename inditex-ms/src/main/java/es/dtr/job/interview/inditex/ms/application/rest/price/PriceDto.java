package es.dtr.job.interview.inditex.ms.application.rest.price;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudElementDto;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Currencies;
import es.dtr.job.interview.inditex.ms.application.rest.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.application.rest.product.ProductDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PriceDto extends CrudElementDto<PriceDto, Long> {

    private Long priceList;
    private BrandDto brand;
    private ProductDto product;
    private Instant startDate;
    private Instant endDate;
    private Integer priority;
    private BigDecimal price;
    private Currencies currency;
    private Instant createdOn;
    private Instant lastUpdatedOn;

    @Override
    public Long getId() {
        return this.priceList;
    }
}
