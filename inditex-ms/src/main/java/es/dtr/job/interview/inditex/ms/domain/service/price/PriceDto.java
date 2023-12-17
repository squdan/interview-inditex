package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.data.Currencies;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

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

}
